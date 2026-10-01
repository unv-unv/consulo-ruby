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

import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfiguration;
import org.jetbrains.plugins.ruby.ruby.run.confuguration.AbstractRubyRunConfigurationParams;

/**
 * @author VISTALL
 * @since 2026-09-30
 */
public interface AbstractRTestsRunConfigurationParams extends AbstractRubyRunConfigurationParams {
    String getTestsFolderPath();

    String getTestScriptPath();

    AbstractRubyRunConfiguration.TestType getTestType();

    String getTestFileMask();

    void setTestsFolderPath(String path);

    void setTestScriptPath(String pathOrMask);

    void setTestType(AbstractRubyRunConfiguration.TestType testType);

    void setTestFileMask(String testFileMask);
}
