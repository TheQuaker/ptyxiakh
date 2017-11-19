/*
 * Copyright (c) 2005-2008 by Masoud Moshref Javadi <moshref@ce.sharif.edu>, http://ce.sharif.edu/~moshref
 * The license.txt file describes the conditions under which this software may be distributed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package edu.sharif.ce.dml.mobisim.evaluationanalyzer.model;

import edu.sharif.ce.dml.common.logic.entity.evaluation.Evaluation;
import edu.sharif.ce.dml.common.parameters.logic.parameterable.ParameterableImplement;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Nov 2, 2007
 * Time: 4:45:28 PM<br>
 *
 */
public abstract class EvaluationAnalyzer extends ParameterableImplement {

    public static final String GROUP_LABEL = "groupname";

    /**
     * @param evaluation input AccuracyEvaluator datas.
     */
    public abstract void setEvaluationData(Evaluation evaluation);

    /**
     * @return created clusters (groups)
     */
    public abstract Collection<EvaluationRecordGroup> getEvaluationGroups();

    public abstract void setConsideringFactorsSize(int size);

    public abstract void reset();

    public List<String> getLabels() {
        return Arrays.asList(GROUP_LABEL);
    }


}
