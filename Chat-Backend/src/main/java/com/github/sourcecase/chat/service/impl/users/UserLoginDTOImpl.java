/*
 * This file is part of blog (https://github.com/jens-meiss/blog).
 *
 *  blog is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  blog is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with blog. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.sourcecase.chat.service.impl.users;

import com.github.sourcecase.chat.service.api.users.UserLoginDTO;

/**
 * The Class UserLoginDTOImpl.
 */
public class UserLoginDTOImpl implements UserLoginDTO {

	private String name;

	private String password;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setName(final String userName) {
		this.name = userName;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
