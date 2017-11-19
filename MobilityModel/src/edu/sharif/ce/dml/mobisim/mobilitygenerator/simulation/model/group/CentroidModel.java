package edu.sharif.ce.dml.mobisim.mobilitygenerator.simulation.model.group;

import edu.sharif.ce.dml.common.logic.entity.Location;
import edu.sharif.ce.dml.mobisim.mobilitygenerator.simulation.logic.GeneratorNode;
import edu.sharif.ce.dml.mobisim.mobilitygenerator.simulation.model.ModelInitializationException;
import edu.sharif.ce.dml.mobisim.mobilitygenerator.simulation.model.maps.PassiveMap;
import edu.sharif.ce.dml.mobisim.mobilitygenerator.simulation.model.maps.exception.InvalidLocationException;

import java.awt.geom.AffineTransform;

import static java.lang.System.out;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 1:30:47 AM
 */
public class CentroidModel extends AbstractGroupModel{

    public CentroidModel() {
    }

    private double[] locArray = {15,15,-15,-15,15,-15,-15,15};
    private int count = 0;

    public void initNode(GeneratorNode node) throws ModelInitializationException {
        NodeInGroup nodeInGroup = nodeNodeInGroup.get(node);
        NodeInGroup leaderInGroup = nodeInGroup.getGroup().getLeader();
        GeneratorNode leaderNode = leaderInGroup.getNode();
        node.setSpeed(generateSpeed(leaderNode));
        stackDepth = 0;
        //Location loc = generateInitialNodeLocation(leaderInGroup.getMovedLocation());
        Location loc = new Location(leaderInGroup.getMovedLocation().getX()+locArray[count],leaderInGroup.getMovedLocation().getY()+locArray[count+1]);
        count++;
        if(count == 4) { count = 0; }
        node.setLocation(loc);
        double angleDev = ((getRandomValue() * ADR * 2) - ADR) * maxAngle;
        node.setDirection(leaderNode.getDirection() + angleDev);

        //added stuff

        Location relevantDistance = new Location(leaderInGroup.getMovedLocation().getX()-node.getDoubleLocation().getX(), leaderInGroup.getMovedLocation().getY()-node.getDoubleLocation().getY());
        node.setRelevantDistance(relevantDistance);
        double initAngle = leaderNode.getDirection();
        node.setInitAngle(initAngle);
        //out.println(relevantDistance);
        //out.println(initAngle);
        //out.println(leaderNode.getLocation());
    }

     protected Location generateInitialNodeLocation(Location leaderLocation) throws ModelInitializationException {
        double xDis = getRandomValue() * maxInitialDistance * 2 - maxInitialDistance;
        double yDis = getRandomValue() * maxInitialDistance * 2 - maxInitialDistance;
        Location tempLocation = new Location(leaderLocation.getX() + xDis, leaderLocation.getY() + yDis);
        stackDepth++;
        try {
            ((PassiveMap) getMap()).validateNode(tempLocation);
        } catch (InvalidLocationException e) {
            if (stackDepth > MAX_STACK_DEPTH) {

                throw new ModelInitializationException("Could not initiate nodes location. Make sure there is" +
                        " enough room for member nodes to be around leaders");
            }
            return generateInitialNodeLocation(leaderLocation);
        }
        return tempLocation;
    }

    @Override
    protected void updateGroupNodeProperties(GeneratorNode node, GeneratorNode leaderNode) {
        //out.println(node.getRelevantDistance());
        double[] pt = {leaderNode.getDoubleLocation().getX() + node.getRelevantDistance().getX(), leaderNode.getDoubleLocation().getY() + node.getRelevantDistance().getY()};
        double theta = leaderNode.getDirection()-node.getInitAngle();
        //out.println(leaderNode.getDirection());

        AffineTransform.getRotateInstance(theta, leaderNode.getDoubleLocation().getX(), leaderNode.getDoubleLocation().getY()).transform(pt, 0, pt, 0, 1);

        //Location destLoc = new Location(leaderNode.getLocation().getX() + node.getRelevantDistance().getX(), leaderNode.getLocation().getY() + node.getRelevantDistance().getY());
        Location destLoc = new Location(pt[0], pt[1]);
        //out.println(destLoc);
        //double newLeaderDir = leaderNode.getDirection();
        //double angleDev = ((getRandomValue() * ADR * 2) - ADR) * maxAngle;
        //double angle = newLeaderDir + angleDev;
        if(node.getDoubleLocation() == destLoc) {
            node.setSpeed(0);
        }else {
            double newSpeed = generateSpeed(leaderNode);
            node.setSpeed(newSpeed*1.08);//increased speed to get into position
            double angle = Location.calculateRadianAngle(node.getDoubleLocation(), destLoc);
            node.setDirection(angle);
        }
    }
}
