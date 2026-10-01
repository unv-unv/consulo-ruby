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
package org.jetbrains.plugins.ruby.ruby.run.confuguration;

import consulo.execution.configuration.ui.SettingsEditor;
import consulo.module.Module;
import consulo.project.Project;
import consulo.ui.Component;
import consulo.ui.annotation.RequiredUIAccess;
import org.jspecify.annotations.Nullable;

/**
 * @author VISTALL
 * @since 2026-09-30
 */
public abstract class AbstractRubyRunConfigurationEditor<C extends AbstractRubyRunConfiguration> extends SettingsEditor<C> {
    protected final Project myProject;
    private final C myConfiguration;

    private @Nullable AbstractRubyRunConfigurationLayout<C> myLayout;

    protected AbstractRubyRunConfigurationEditor(Project project, C configuration) {
        myProject = project;
        myConfiguration = configuration;
    }

    @RequiredUIAccess
    protected abstract AbstractRubyRunConfigurationLayout<C> createLayout(Module[] modules);

    @RequiredUIAccess
    @Override
    protected Component createUIComponent() {
        AbstractRubyRunConfigurationLayout<C> layout = createLayout(myConfiguration.getModules());
        myLayout = layout;
        return layout.build();
    }

    @RequiredUIAccess
    @Override
    protected void resetEditorFrom(C configuration) {
        AbstractRubyRunConfigurationLayout<C> layout = myLayout;
        if (layout != null) {
            layout.reset(configuration);
        }
    }

    @RequiredUIAccess
    @Override
    protected void applyEditorTo(C configuration) {
        AbstractRubyRunConfigurationLayout<C> layout = myLayout;
        if (layout != null) {
            layout.apply(configuration);
        }
    }
}
