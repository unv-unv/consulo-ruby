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

package org.jetbrains.plugins.ruby.ruby.run.confuguration;

import jakarta.annotation.Nonnull;

import jakarta.annotation.Nullable;

import consulo.fileChooser.FileChooserDescriptorFactory;
import consulo.fileChooser.FileChooserDescriptor;
import consulo.project.Project;
import consulo.ui.ex.awt.LabeledComponent;
import consulo.ui.ex.awt.TextFieldWithBrowseButton;
import consulo.util.lang.ref.Ref;
import consulo.execution.ui.awt.RawCommandLineEditor;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Roman Chernyatchik
 * @date: Oct 20, 2007
 */
public class RubyRunConfigurationUIUtil
{
	public static LabeledComponent createDirChooserComponent(final Ref<TextFieldWithBrowseButton> dirTFWrapper, final String text)
	{
		final TextFieldWithBrowseButton dirTextField = new TextFieldWithBrowseButton();
		dirTFWrapper.set(dirTextField);

		LabeledComponent<TextFieldWithBrowseButton> myComponent = new LabeledComponent<TextFieldWithBrowseButton>();
		myComponent.setComponent(dirTextField);
		myComponent.setText(text);

		return myComponent;
	}

	public static LabeledComponent<RawCommandLineEditor> createRawEditorComponent(final Ref<RawCommandLineEditor> rawEditorWrapper, final String dialogCaption, final String labelTextWithMnemonic)
	{
		final RawCommandLineEditor rawEditor = new RawCommandLineEditor();
		rawEditorWrapper.set(rawEditor);

		rawEditor.setDialogCaption(dialogCaption);

		LabeledComponent<RawCommandLineEditor> myComponent = new LabeledComponent<RawCommandLineEditor>();
		myComponent.setComponent(rawEditor);
		myComponent.setText(labelTextWithMnemonic);

		return myComponent;
	}

	public static LabeledComponent<TextFieldWithBrowseButton> createScriptPathComponent(final Ref<TextFieldWithBrowseButton> testScriptTextFieldWrapper, final String text)
	{
		final TextFieldWithBrowseButton testScriptTextField = new TextFieldWithBrowseButton();
		testScriptTextFieldWrapper.set(testScriptTextField);

		LabeledComponent<TextFieldWithBrowseButton> myComponent = new LabeledComponent<TextFieldWithBrowseButton>();
		myComponent.setComponent(testScriptTextField);
		myComponent.setText(text);

		return myComponent;
	}

	public static FileChooserDescriptor addFolderChooser(@Nonnull final String title, @Nonnull final TextFieldWithBrowseButton textField, final Project project)
	{
		final FileChooserDescriptor folderChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
		folderChooserDescriptor.setTitle(title);
		textField.addBrowseFolderListener(title, null, project, folderChooserDescriptor);
		return folderChooserDescriptor;
	}

	public static FileChooserDescriptor addFileChooser(@Nonnull final String title, @Nonnull final TextFieldWithBrowseButton textField, @Nullable final Project project)
	{
		final FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor();
		fileChooserDescriptor.setTitle(title);
		textField.addBrowseFolderListener(title, null, project, fileChooserDescriptor);
		return fileChooserDescriptor;
	}
}
