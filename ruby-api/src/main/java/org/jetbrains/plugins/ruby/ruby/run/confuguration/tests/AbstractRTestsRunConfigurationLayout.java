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
import consulo.fileChooser.FileChooserDescriptorFactory;
import consulo.fileChooser.FileChooserTextBoxBuilder;
import consulo.localize.LocalizeValue;
import consulo.module.Module;
import consulo.project.Project;
import consulo.ruby.api.localize.RubyApiLocalize;
import consulo.ui.Label;
import consulo.ui.RadioGroup;
import consulo.ui.TextBox;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.FormBuilder;
import consulo.util.lang.StringUtil;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfiguration;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfiguration.TestType;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfigurationLayout;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * @author VISTALL
 * @since 2026-09-30
 */
public abstract class AbstractRTestsRunConfigurationLayout<C extends AbstractRubyRunConfiguration & AbstractRTestsRunConfigurationParams>
    extends AbstractRubyRunConfigurationLayout<C> {

    private final RadioGroup<TestType> myTestTypeGroup;
    private final FileChooserTextBoxBuilder.Controller myFolderBox;
    private final TextBox myFileMaskBox;
    private final FileChooserTextBoxBuilder.Controller myScriptBox;

    private @Nullable Label myFolderLabel;
    private @Nullable Label myFileMaskLabel;
    private @Nullable Label myScriptLabel;

    @RequiredUIAccess
    protected AbstractRTestsRunConfigurationLayout(Project project, Disposable uiDisposable, Module[] modules) {
        super(project, uiDisposable, modules);

        myTestTypeGroup = RadioGroup.create();
        myFolderBox = createPathBox(
            FileChooserDescriptorFactory.createSingleFolderDescriptor(),
            RubyApiLocalize.runConfigurationMessagesSelectFolderPath()
        );
        myFileMaskBox = TextBox.create();
        myScriptBox = createPathBox(
            FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor(),
            RubyApiLocalize.runConfigurationMessagesSelectRubySciptPath()
        );

        myTestTypeGroup.addValueListener(this::onTestTypeSelected);
    }

    protected abstract List<TestType> getTestTypes();

    protected abstract LocalizeValue getTestTypeName(TestType testType);

    protected abstract LocalizeValue getTestTypeLabel();

    protected abstract LocalizeValue getFolderLabel();

    protected abstract LocalizeValue getFileMaskLabel();

    protected abstract LocalizeValue getScriptLabel();

    protected abstract String getDefaultFileMask();

    @RequiredUIAccess
    @Override
    protected void addBefore(FormBuilder builder) {
        builder.addLabeled(getTestTypeLabel(), myTestTypeGroup.fillHorizontal(getTestTypes(), this::getTestTypeName));

        myFolderLabel = addRow(builder, getFolderLabel(), myFolderBox.getComponent());
        myFileMaskLabel = addRow(builder, getFileMaskLabel(), myFileMaskBox);
        myScriptLabel = addRow(builder, getScriptLabel(), myScriptBox.getComponent());
    }

    @RequiredUIAccess
    protected void updateTestType(TestType testType) {
        boolean allInFolder = testType == TestType.ALL_IN_FOLDER;

        setRowVisible(myFolderLabel, myFolderBox.getComponent(), allInFolder);
        setRowVisible(myFileMaskLabel, myFileMaskBox, allInFolder);
        setRowVisible(myScriptLabel, myScriptBox.getComponent(), !allInFolder);
    }

    @RequiredUIAccess
    private void onTestTypeSelected(@Nullable TestType testType) {
        if (testType == null) {
            return;
        }

        if (testType == TestType.ALL_IN_FOLDER && StringUtil.isEmpty(myFileMaskBox.getValue())) {
            myFileMaskBox.setValue(getDefaultFileMask());
        }

        updateTestType(testType);
    }

    @RequiredUIAccess
    @Override
    public void reset(C configuration) {
        super.reset(configuration);

        setPath(myFolderBox, configuration.getTestsFolderPath());
        myFileMaskBox.setValue(StringUtil.notNullize(configuration.getTestFileMask()));
        setPath(myScriptBox, configuration.getTestScriptPath());

        TestType testType = configuration.getTestType();
        myTestTypeGroup.setValue(testType, false);
        updateTestType(testType);
    }

    @RequiredUIAccess
    @Override
    public void apply(C configuration) {
        super.apply(configuration);

        TestType testType = myTestTypeGroup.getValue();
        if (testType != null) {
            configuration.setTestType(testType);
        }
        configuration.setTestsFolderPath(getPath(myFolderBox));
        configuration.setTestFileMask(getText(myFileMaskBox));
        configuration.setTestScriptPath(getPath(myScriptBox));
    }
}
