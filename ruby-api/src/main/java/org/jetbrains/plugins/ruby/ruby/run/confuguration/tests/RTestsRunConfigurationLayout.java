/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.plugins.ruby.ruby.run.confuguration.tests;

import consulo.disposer.Disposable;
import consulo.localize.LocalizeValue;
import consulo.module.Module;
import consulo.project.Project;
import consulo.ruby.api.localize.RubyApiLocalize;
import consulo.ui.CheckBox;
import consulo.ui.Label;
import consulo.ui.TextBox;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.FormBuilder;
import consulo.util.lang.StringUtil;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfiguration.TestType;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * @author VISTALL
 * @since 2026-09-30
 */
public class RTestsRunConfigurationLayout extends AbstractRTestsRunConfigurationLayout<RTestsRunConfiguration> {
    private final TextBox myClassBox;
    private final TextBox myMethodBox;
    private final CheckBox myInheritanceCheckDisabledBox;

    private @Nullable Label myClassLabel;
    private @Nullable Label myMethodLabel;

    @RequiredUIAccess
    public RTestsRunConfigurationLayout(Project project, Disposable uiDisposable, Module[] modules) {
        super(project, uiDisposable, modules);

        myClassBox = TextBox.create();
        myMethodBox = TextBox.create();
        myInheritanceCheckDisabledBox = CheckBox.create(RubyApiLocalize.runConfigurationTestIgnoreTestcaseInheritance());
    }

    @Override
    protected List<TestType> getTestTypes() {
        return List.of(TestType.ALL_IN_FOLDER, TestType.TEST_SCRIPT, TestType.TEST_CLASS, TestType.TEST_METHOD);
    }

    @Override
    protected LocalizeValue getTestTypeName(TestType testType) {
        return switch (testType) {
            case ALL_IN_FOLDER -> RubyApiLocalize.runConfigurationTestsDialogRbAllinfolder();
            case TEST_SCRIPT -> RubyApiLocalize.runConfigurationTestsDialogRbFile();
            case TEST_CLASS -> RubyApiLocalize.runConfigurationTestsDialogRbClass();
            case TEST_METHOD -> RubyApiLocalize.runConfigurationTestsDialogRbMethod();
        };
    }

    @Override
    protected LocalizeValue getTestTypeLabel() {
        return RubyApiLocalize.runConfigurationTestFormTesttypeTitle();
    }

    @Override
    protected LocalizeValue getFolderLabel() {
        return LocalizeValue.join(RubyApiLocalize.runConfigurationTestsDialogComponentsFolder(), LocalizeValue.colon());
    }

    @Override
    protected LocalizeValue getFileMaskLabel() {
        return RubyApiLocalize.runConfigurationTestsDialogComponentsSearchMask();
    }

    @Override
    protected LocalizeValue getScriptLabel() {
        return RubyApiLocalize.runConfigurationMessagesScriptPath();
    }

    @Override
    protected String getDefaultFileMask() {
        return RTestsRunConfiguration.DEFAULT_TESTS_SEARCH_MASK;
    }

    @RequiredUIAccess
    @Override
    protected void addBefore(FormBuilder builder) {
        super.addBefore(builder);

        myClassLabel = addRow(builder, RubyApiLocalize.runConfigurationTestsMessagesClassName(), myClassBox);
        myMethodLabel = addRow(builder, RubyApiLocalize.runConfigurationTestsMessagesMethodName(), myMethodBox);
    }

    @RequiredUIAccess
    @Override
    protected void addBottom(FormBuilder builder) {
        builder.addBottom(myInheritanceCheckDisabledBox);
    }

    @RequiredUIAccess
    @Override
    protected void updateTestType(TestType testType) {
        super.updateTestType(testType);

        boolean testClass = testType == TestType.TEST_CLASS || testType == TestType.TEST_METHOD;
        setRowVisible(myClassLabel, myClassBox, testClass);
        setRowVisible(myMethodLabel, myMethodBox, testType == TestType.TEST_METHOD);
        myInheritanceCheckDisabledBox.setVisible(testClass);
    }

    @RequiredUIAccess
    @Override
    public void reset(RTestsRunConfiguration configuration) {
        super.reset(configuration);

        myClassBox.setValue(StringUtil.notNullize(configuration.getTestQualifiedClassName()));
        myMethodBox.setValue(StringUtil.notNullize(configuration.getTestMethodName()));
        myInheritanceCheckDisabledBox.setValue(configuration.isInheritanceCheckDisabled());
    }

    @RequiredUIAccess
    @Override
    public void apply(RTestsRunConfiguration configuration) {
        super.apply(configuration);

        configuration.setTestQualifiedClassName(getText(myClassBox));
        configuration.setTestMethodName(getText(myMethodBox));
        configuration.setInheritanceCheckDisabled(myInheritanceCheckDisabledBox.getValueOrError());
    }
}
