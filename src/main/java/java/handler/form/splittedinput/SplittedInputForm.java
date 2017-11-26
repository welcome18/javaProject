/*
 * Copyright 2014 astamuse company,Ltd.
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
package java.handler.form.splittedinput;

import java.handler.form.PersonForm;
import java.handler.form.multiinput.CascadeJobForm;
import com.astamuse.asta4d.web.form.annotation.CascadeFormField;
import com.astamuse.asta4d.web.form.annotation.Form;
import com.astamuse.asta4d.web.form.annotation.renderable.AvailableWhenEditOnly;
import com.astamuse.asta4d.web.form.flow.base.StepAwaredValidationTarget;
import com.astamuse.asta4d.web.form.flow.base.StepRepresentableForm;
import com.astamuse.asta4d.web.form.flow.classical.ClassicalFormFlowConstant;
import com.astamuse.asta4d.web.form.flow.ext.ExcludingFieldRetrievableForm;
import com.astamuse.asta4d.web.form.flow.ext.IncludingFieldRetrievableForm;
import com.astamuse.asta4d.web.form.flow.ext.MultiInputStepForm;

//@ShowCode:showSplittedFormStart
/**
 * Declares four form instances to represent the steps: input-1, input-2, input-3, confirm(and complete).
 * 
 * @author e-ryu
 *
 */
@Form
public class SplittedInputForm implements MultiInputStepForm {

    /**
     * Implements {@link ExcludingFieldRetrievableForm} to declare fields to be excluded in validation and rendering. Implements
     * {@link StepRepresentableForm} to declare whether this form instance should be rendered.
     * 
     * @author e-ryu
     *
     */
    @Form
    public static class PersonFormStep1 extends PersonForm implements ExcludingFieldRetrievableForm, StepRepresentableForm {

        @Override
        public String[] retrieveRepresentingSteps() {
            return new String[] { "input-1" };
        }

        @Override
        public String[] getExcludeFields() {
            return new String[] { "language", "memo" };
        }

    }

    /**
     * Use {@link IncludingFieldRetrievableForm} instead of {@link ExcludingFieldRetrievableForm} to simplify fields declaration.
     * 
     * @author e-ryu
     *
     */
    @Form
    public static class PersonFormStep2 extends PersonForm implements IncludingFieldRetrievableForm, StepRepresentableForm {

        @Override
        public String[] retrieveRepresentingSteps() {
            return new String[] { "input-2" };
        }

        @Override
        public String[] getIncludeFields() {
            return new String[] { "language", "memo" };
        }

    }

    @Form
    public static class CascadeJobFormStep3 extends CascadeJobForm implements StepRepresentableForm {
        @Override
        public String[] retrieveRepresentingSteps() {
            return new String[] { "input-3" };
        }
    }

    @Form
    public static class ConfirmStepForm implements StepRepresentableForm {

        @CascadeFormField
        private PersonForm personForm = new PersonForm();

        @CascadeFormField
        private CascadeJobForm cascadeJobForm = new CascadeJobForm();

        @Override
        public String[] retrieveRepresentingSteps() {
            return new String[] { ClassicalFormFlowConstant.STEP_CONFIRM, ClassicalFormFlowConstant.STEP_COMPLETE };
        }

        public PersonForm getPersonForm() {
            return personForm;
        }

        public CascadeJobForm getCascadeJobForm() {
            return cascadeJobForm;
        }

    }

    // private

    // show the input comments only when edit mode
    @AvailableWhenEditOnly(selector = "#input-comment")
    private String inputComment;

    /**
     * {@link StepAwaredValidationTarget} to suggest the validation target for certain step.
     */
    @CascadeFormField
    @StepAwaredValidationTarget(inputStep1)
    private PersonFormStep1 personFormStep1;

    @CascadeFormField
    @StepAwaredValidationTarget(inputStep2)
    private PersonFormStep2 personFormStep2;

    @CascadeFormField
    @StepAwaredValidationTarget(inputStep3)
    private CascadeJobFormStep3 cascadeJobFormStep3;

    @CascadeFormField
    private ConfirmStepForm confirmStepForm;

    public static final String inputStep1 = "input-1";

    public static final String inputStep2 = "input-2";

    public static final String inputStep3 = "input-3";

    public SplittedInputForm() {
        this.personFormStep1 = new PersonFormStep1();
        this.personFormStep2 = new PersonFormStep2();
        this.cascadeJobFormStep3 = new CascadeJobFormStep3();
        this.confirmStepForm = new ConfirmStepForm();
    }

    // getter/setter

    public void setForms(PersonForm personForm, CascadeJobForm cascadeJobForm) {
        this.personFormStep1.copyPropertiesFrom(personForm);
        this.personFormStep2.copyPropertiesFrom(personForm);
        this.cascadeJobFormStep3.copyPropertiesFrom(cascadeJobForm);
    }

    public ConfirmStepForm getForms() {
        return this.confirmStepForm;
    }

    @Override
    public void mergeInputDataForConfirm(String step, Object inputForm) {
        SplittedInputForm form = (SplittedInputForm) inputForm;
        switch (step) {
        case inputStep1:
            form.personFormStep1.copyIncludingFieldsTo(this.confirmStepForm.personForm);
            break;
        case inputStep2:
            form.personFormStep2.copyIncludingFieldsTo(this.confirmStepForm.personForm);
            break;
        case inputStep3:
            form.cascadeJobFormStep3.copyPropertiesTo(this.confirmStepForm.cascadeJobForm);
            break;
        default:
            //
        }
    }

}
// @ShowCode:showSplittedFormEnd
