/*
 * Copyright 2012 astamuse company,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package java.snippet.form;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.handler.form.PersonForm;
import java.handler.form.cascade.EducationForm;
import java.util.persondb.Person.BloodType;
import java.util.persondb.Person.Language;
import java.util.persondb.Person.SEX;
import com.astamuse.asta4d.web.form.field.FormFieldPrepareRenderer;
import com.astamuse.asta4d.web.form.field.OptionValueMap;
import com.astamuse.asta4d.web.form.field.OptionValuePair;
import com.astamuse.asta4d.web.form.field.impl.CheckboxPrepareRenderer;
import com.astamuse.asta4d.web.form.field.impl.RadioPrepareRenderer;
import com.astamuse.asta4d.web.form.field.impl.SelectPrepareRenderer;
import com.astamuse.asta4d.web.form.flow.classical.ClassicalMultiStepFormFlowSnippetTrait;

//@ShowCode:showCascadeFormSnippetStart
public class CascadeFormSnippet implements ClassicalMultiStepFormFlowSnippetTrait {
    @Override
    public List<FormFieldPrepareRenderer> retrieveFieldPrepareRenderers(String renderTargetStep, Object form) {
        List<FormFieldPrepareRenderer> list = new LinkedList<>();

        // since there are cascade forms, so we have to differentiate the option data rendering for different forms
        if (form instanceof PersonForm) {
            list.add(new SelectPrepareRenderer(PersonForm.class, "bloodtype").setOptionData(BloodType.asOptionValueMap));
            list.add(new RadioPrepareRenderer(PersonForm.class, "sex").setOptionData(SEX.asOptionValueMap));
            list.add(new CheckboxPrepareRenderer(PersonForm.class, "language").setOptionData(Language.asOptionValueMap));
        } else if (form instanceof EducationForm) {
            // to render the option data of arrayed form fields, simply use the field name with "@" mark as the same as plain fields
            list.add(new SelectPrepareRenderer(EducationForm.class, "education-year-@").setOptionData(createYearOptionList()));
        }
        return list;
    }

    private OptionValueMap createYearOptionList() {
        List<OptionValuePair> optionList = new ArrayList<>();
        for (int y = 2000; y <= 2010; y++) {
            optionList.add(new OptionValuePair(String.valueOf(y), String.valueOf(y)));
        }
        return new OptionValueMap(optionList);
    }

    @Override
    public Object createFormInstanceForCascadeFormArrayTemplate(Class subFormType) throws InstantiationException, IllegalAccessException {
        Object form = ClassicalMultiStepFormFlowSnippetTrait.super.createFormInstanceForCascadeFormArrayTemplate(subFormType);
        ((EducationForm) form).setPersonId(-1);
        return form;
    }

    @Override
    public String clientCascadeUtilJsExportName() {
        return "$acu";
    }
}
// @ShowCode:showCascadeFormSnippetEnd
