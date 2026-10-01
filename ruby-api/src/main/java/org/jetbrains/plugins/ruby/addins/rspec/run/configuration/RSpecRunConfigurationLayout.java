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
package org.jetbrains.plugins.ruby.addins.rspec.run.configuration;

import consulo.disposer.Disposable;
import consulo.fileChooser.FileChooserDescriptorFactory;
import consulo.fileChooser.FileChooserTextBoxBuilder;
import consulo.localize.LocalizeValue;
import consulo.module.Module;
import consulo.project.Project;
import consulo.ruby.api.localize.RubyApiLocalize;
import consulo.ui.CheckBox;
import consulo.ui.TextBoxWithExpandAction;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.FormBuilder;
import consulo.util.lang.StringUtil;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfiguration.TestType;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.tests.AbstractRTestsRunConfigurationLayout;

import java.util.List;

/**
 * @author VISTALL
 * @since 2026-09-30
 */
public class RSpecRunConfigurationLayout extends AbstractRTestsRunConfigurationLayout<RSpecRunConfiguration> {
    private final TextBoxWithExpandAction mySpecArgumentsBox;
    private final FileChooserTextBoxBuilder.Controller myCustomRunnerBox;
    private final CheckBox myColouredOutputBox;
    private final CheckBox myRunSeparatelyBox;

    @RequiredUIAccess
    public RSpecRunConfigurationLayout(Project project, Disposable uiDisposable, Module[] modules) {
        super(project, uiDisposable, modules);

        mySpecArgumentsBox = createArgumentsBox(RubyApiLocalize.rspecRunConfigurationMessagesEditSpecArgs());
        myCustomRunnerBox = createPathBox(
            FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor(),
            RubyApiLocalize.rspecRunConfigurationTestsDialogComponentsSelectSpecCustomRunner()
        );
        myCustomRunnerBox.getComponent().setPlaceholder(RubyApiLocalize.rspecRunConfigurationCustomRunnerPlaceholder());
        myColouredOutputBox = CheckBox.create(RubyApiLocalize.rspecRunConfigurationTestsColouredOutput());
        myRunSeparatelyBox = CheckBox.create(RubyApiLocalize.rspecRunConfigurationTestsRunnerRunSeparately());
    }

    @Override
    protected List<TestType> getTestTypes() {
        return List.of(TestType.ALL_IN_FOLDER, TestType.TEST_SCRIPT);
    }

    @Override
    protected LocalizeValue getTestTypeName(TestType testType) {
        return testType == TestType.ALL_IN_FOLDER
            ? RubyApiLocalize.rspecRunConfigurationTestsDialogRbAllinfolder()
            : RubyApiLocalize.rspecRunConfigurationTestsDialogRbFile();
    }

    @Override
    protected LocalizeValue getTestTypeLabel() {
        return RubyApiLocalize.rspecRunConfigurationTestsFormTesttypeTitle();
    }

    @Override
    protected LocalizeValue getFolderLabel() {
        return LocalizeValue.join(RubyApiLocalize.rspecRunConfigurationTestsDialogComponentsFolder(), LocalizeValue.colon());
    }

    @Override
    protected LocalizeValue getFileMaskLabel() {
        return RubyApiLocalize.rspecRunConfigurationTestsDialogComponentsSearchMask();
    }

    @Override
    protected LocalizeValue getScriptLabel() {
        return RubyApiLocalize.rspecRunConfigurationMessagesScriptPath();
    }

    @Override
    protected String getDefaultFileMask() {
        return RSpecRunConfiguration.DEFAULT_TESTS_SEARCH_MASK;
    }

    @RequiredUIAccess
    @Override
    protected void addBefore(FormBuilder builder) {
        super.addBefore(builder);

        builder.addLabeled(
            LocalizeValue.join(RubyApiLocalize.rspecRunConfigurationMessagesSpecArgs(), LocalizeValue.colon()),
            mySpecArgumentsBox
        );
        builder.addLabeled(
            LocalizeValue.join(RubyApiLocalize.rspecRunConfigurationTestsDialogComponentsSpecCustomRunner(), LocalizeValue.colon()),
            myCustomRunnerBox.getComponent()
        );
    }

    @RequiredUIAccess
    @Override
    protected void addBottom(FormBuilder builder) {
        builder.addBottom(myColouredOutputBox);
        builder.addBottom(myRunSeparatelyBox);
    }

    @RequiredUIAccess
    @Override
    protected void updateTestType(TestType testType) {
        super.updateTestType(testType);

        myRunSeparatelyBox.setVisible(testType == TestType.ALL_IN_FOLDER);
    }

    @RequiredUIAccess
    @Override
    public void reset(RSpecRunConfiguration configuration) {
        super.reset(configuration);

        mySpecArgumentsBox.setValue(StringUtil.notNullize(configuration.getSpecArgs()));
        setPath(myCustomRunnerBox, configuration.shouldUseCustomSpecRunner() ? configuration.getCustomSpecsRunnerPath() : null);
        myColouredOutputBox.setValue(configuration.shouldUseColoredOutput());
        myRunSeparatelyBox.setValue(configuration.shouldRunSpecSeparately());
    }

    @RequiredUIAccess
    @Override
    public void apply(RSpecRunConfiguration configuration) {
        super.apply(configuration);

        configuration.setSpecArgs(getText(mySpecArgumentsBox));

        String customRunnerPath = getPath(myCustomRunnerBox);
        boolean useCustomRunner = !customRunnerPath.isEmpty();
        configuration.setShouldUseCustomSpecRunner(useCustomRunner);
        if (useCustomRunner) {
            configuration.setCustomSpecsRunnerPath(customRunnerPath);
        }

        configuration.setShouldUseColoredOutput(myColouredOutputBox.getValueOrError());
        configuration.setShouldRunSpecSeparately(myRunSeparatelyBox.getValueOrError());
    }
}
