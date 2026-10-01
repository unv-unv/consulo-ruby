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
package org.jetbrains.plugins.ruby.ruby.run.confuguration.rubyScript;

import consulo.disposer.Disposable;
import consulo.fileChooser.FileChooserDescriptorFactory;
import consulo.fileChooser.FileChooserTextBoxBuilder;
import consulo.localize.LocalizeValue;
import consulo.module.Module;
import consulo.project.Project;
import consulo.ruby.api.localize.RubyApiLocalize;
import consulo.ui.TextBoxWithExpandAction;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.util.FormBuilder;
import consulo.util.lang.StringUtil;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfigurationLayout;
import org.jspecify.annotations.Nullable;

/**
 * @author VISTALL
 * @since 2026-09-30
 */
public class RubyRunConfigurationLayout<C extends RubyRunConfiguration> extends AbstractRubyRunConfigurationLayout<C> {
    private final FileChooserTextBoxBuilder.Controller myScriptPathBox;
    private final TextBoxWithExpandAction myScriptArgumentsBox;

    @RequiredUIAccess
    public RubyRunConfigurationLayout(Project project, Disposable uiDisposable, Module[] modules) {
        super(project, uiDisposable, modules);

        myScriptPathBox = createPathBox(
            FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor(),
            RubyApiLocalize.runConfigurationMessagesSelectRubySciptPath()
        );
        myScriptArgumentsBox = createArgumentsBox(RubyApiLocalize.runConfigurationMessagesEditScriptArgs());
    }

    @RequiredUIAccess
    @Override
    protected void addBefore(FormBuilder builder) {
        addScriptPathRow(builder);
        addScriptArgumentsRow(builder, LocalizeValue.join(RubyApiLocalize.runConfigurationMessagesScriptArgs(), LocalizeValue.colon()));
    }

    @RequiredUIAccess
    protected final void addScriptPathRow(FormBuilder builder) {
        builder.addLabeled(RubyApiLocalize.runConfigurationMessagesScriptPath(), myScriptPathBox.getComponent());
    }

    @RequiredUIAccess
    protected final void addScriptArgumentsRow(FormBuilder builder, LocalizeValue label) {
        builder.addLabeled(label, myScriptArgumentsBox);
    }

    @RequiredUIAccess
    protected final void setScriptPath(@Nullable String path) {
        setPath(myScriptPathBox, path);
    }

    @RequiredUIAccess
    @Override
    public void reset(C configuration) {
        super.reset(configuration);

        setPath(myScriptPathBox, configuration.getScriptPath());
        myScriptArgumentsBox.setValue(StringUtil.notNullize(configuration.getScriptArgs()));
    }

    @RequiredUIAccess
    @Override
    public void apply(C configuration) {
        super.apply(configuration);

        configuration.setScriptPath(getPath(myScriptPathBox));
        configuration.setScriptArgs(getText(myScriptArgumentsBox));
    }
}
