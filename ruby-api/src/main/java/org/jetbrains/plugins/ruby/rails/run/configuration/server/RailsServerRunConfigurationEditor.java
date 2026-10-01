/*
 * Copyright 2000-2008 JetBrains s.r.o.
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

import consulo.module.Module;
import consulo.project.Project;
import consulo.ui.annotation.RequiredUIAccess;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfigurationEditor;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfigurationLayout;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Roman Chernyatchik
 * @date: 04.08.2007
 */
public class RailsServerRunConfigurationEditor extends AbstractRubyRunConfigurationEditor<RailsServerRunConfiguration>
{
	public RailsServerRunConfigurationEditor(Project project, RailsServerRunConfiguration runConfiguration)
	{
		super(project, runConfiguration);
	}

	@RequiredUIAccess
	@Override
	protected AbstractRubyRunConfigurationLayout<RailsServerRunConfiguration> createLayout(Module[] modules)
	{
		return new RailsServerRunConfigurationLayout(myProject, this, modules);
	}
}
