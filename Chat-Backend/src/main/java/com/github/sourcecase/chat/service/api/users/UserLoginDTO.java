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
package com.github.sourcecase.chat.service.api.users;

/**
 * The Interface UserLoginDTO.
 *
 * @author Jens Meiß
 * @created 2014-04-30
 */
public interface UserLoginDTO {

	public String getName();

	public String getPassword();

}
