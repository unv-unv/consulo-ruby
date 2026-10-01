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

package org.jetbrains.plugins.ruby.ruby.run.confuguration.tests;

import consulo.module.Module;
import consulo.project.Project;
import consulo.ui.annotation.RequiredUIAccess;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfigurationEditor;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfigurationLayout;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Roman Chernyatchik
 * @date: 19.07.2007
 */
public class RTestsRunConfigurationEditor extends AbstractRubyRunConfigurationEditor<RTestsRunConfiguration>
{
	public RTestsRunConfigurationEditor(Project project, RTestsRunConfiguration configuration)
	{
		super(project, configuration);
	}

	@RequiredUIAccess
	@Override
	protected AbstractRubyRunConfigurationLayout<RTestsRunConfiguration> createLayout(Module[] modules)
	{
		return new RTestsRunConfigurationLayout(myProject, this, modules);
	}
}
