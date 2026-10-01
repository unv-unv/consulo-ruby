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
package org.jetbrains.plugins.ruby.rails.run.configuration.server;

import consulo.disposer.Disposable;
import consulo.localize.LocalizeValue;
import consulo.module.Module;
import consulo.project.Project;
import consulo.ruby.api.localize.RubyApiLocalize;
import consulo.ui.ComboBox;
import consulo.ui.RadioGroup;
import consulo.ui.TextBox;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.layout.DockLayout;
import consulo.ui.layout.HorizontalLayout;
import consulo.ui.util.FormBuilder;
import consulo.util.lang.StringUtil;
import org.jetbrains.plugins.ruby.rails.run.configuration.server.RailsServerRunConfiguration.RailsEnvironmentType;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.rubyScript.RubyRunConfigurationLayout;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 2026-09-30
 */
public class RailsServerRunConfigurationLayout extends RubyRunConfigurationLayout<RailsServerRunConfiguration> {
    private final Map<String, @Nullable String> myServerScriptPaths = new HashMap<>();
    private final Map<String, @Nullable String> myWorkingDirectories = new HashMap<>();

    private final RadioGroup<Boolean> myChoosePortManuallyGroup;
    private final TextBox myPortBox;
    private final TextBox myIpAddressBox;
    private final ComboBox<RailsEnvironmentType> myEnvironmentBox;

    @RequiredUIAccess
    public RailsServerRunConfigurationLayout(Project project, Disposable uiDisposable, Module[] modules) {
        super(project, uiDisposable, modules);

        for (Module module : modules) {
            myServerScriptPaths.put(module.getName(), RailsServerRunConfiguration.getServerScriptPathByModule(module));
            myWorkingDirectories.put(module.getName(), RailsServerRunConfiguration.getRailsWorkDirByModule(module));
        }

        myChoosePortManuallyGroup = RadioGroup.create();
        myPortBox = TextBox.create();
        myIpAddressBox = TextBox.create();
        myEnvironmentBox = ComboBox.create(RailsEnvironmentType.values());
        myEnvironmentBox.setTextRenderer(type -> type == null ? LocalizeValue.empty() : LocalizeValue.of(type.getParamName()));

        myChoosePortManuallyGroup.addValueListener(manually -> myPortBox.setEnabled(Boolean.TRUE.equals(manually)));
        addModuleListener(this::onModuleSelected);
    }

    @RequiredUIAccess
    @Override
    protected void addBefore(FormBuilder builder) {
        addScriptPathRow(builder);

        HorizontalLayout portChoiceLayout = myChoosePortManuallyGroup.fillHorizontal(
            List.of(Boolean.FALSE, Boolean.TRUE),
            manually -> manually
                ? RubyApiLocalize.runConfigurationServerDialogManuallyPort()
                : RubyApiLocalize.runConfigurationServerDialogUseFreePort()
        );

        builder.addLabeled(
            LocalizeValue.join(RubyApiLocalize.runConfigurationServerDialogPort(), LocalizeValue.colon()),
            DockLayout.create().left(portChoiceLayout).center(myPortBox)
        );
        builder.addLabeled(LocalizeValue.join(RubyApiLocalize.runConfigurationServerDialogIp(), LocalizeValue.colon()), myIpAddressBox);
        builder.addLabeled(
            LocalizeValue.join(RubyApiLocalize.runConfigurationServerDialogEnvironment(), LocalizeValue.colon()),
            myEnvironmentBox
        );

        addScriptArgumentsRow(builder, LocalizeValue.join(RubyApiLocalize.runConfigurationServerArgs(), LocalizeValue.colon()));
    }

    @RequiredUIAccess
    private void onModuleSelected(String moduleName) {
        setScriptPath(myServerScriptPaths.get(moduleName));
        setWorkingDirectory(myWorkingDirectories.get(moduleName));
    }

    @RequiredUIAccess
    @Override
    public void reset(RailsServerRunConfiguration configuration) {
        super.reset(configuration);

        boolean choosePortManually = configuration.isChoosePortManually();
        myChoosePortManuallyGroup.setValue(choosePortManually, false);
        myPortBox.setEnabled(choosePortManually);
        myPortBox.setValue(StringUtil.notNullize(configuration.getPort()));
        myIpAddressBox.setValue(StringUtil.notNullize(configuration.getIPAddr()));
        myEnvironmentBox.setValue(configuration.getRailsEnvironmentType(), false);
    }

    @RequiredUIAccess
    @Override
    public void apply(RailsServerRunConfiguration configuration) {
        super.apply(configuration);

        configuration.setChoosePortManually(Boolean.TRUE.equals(myChoosePortManuallyGroup.getValue()));
        configuration.setPort(getText(myPortBox));
        configuration.setIPAddr(getText(myIpAddressBox));

        RailsEnvironmentType environmentType = myEnvironmentBox.getValue();
        if (environmentType != null) {
            configuration.setRailsEnvironmentType(environmentType);
        }
    }
}
