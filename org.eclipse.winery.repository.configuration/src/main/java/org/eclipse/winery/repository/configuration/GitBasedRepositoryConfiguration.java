/**
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 * - Oliver Kopp - initial API and implementation
 */

package org.eclipse.winery.repository.configuration;

import java.util.Objects;

public class GitBasedRepositoryConfiguration extends FileBasedRepositoryConfiguration {

	private boolean autoCommit;

	public GitBasedRepositoryConfiguration(boolean autoCommit, FileBasedRepositoryConfiguration fileBasedRepositoryConfiguration) {
		super(Objects.requireNonNull(fileBasedRepositoryConfiguration));
		this.autoCommit = autoCommit;
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}
}
