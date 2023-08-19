/**
 * Copyright 2021 Onsiea All rights reserved.
 * <p>
 * This file is part of OnsieaLogger. (<a href="https://github.com/Onsiea/OnsieaLogger">...</a>)
 * <p>
 * Unless noted in license (<a href="https://github.com/Onsiea/OnsieaLogger/blob/main/LICENSE.md">...</a>) notice file
 * (<a href="https://github.com/Onsiea/OnsieaLogger/blob/main/LICENSE_NOTICE.md">...</a>), OnsieaLogger and all parts
 * herein is licensed under the terms of the LGPL-3.0 (<a href="https://www.gnu.org/licenses/lgpl-3.0.html">...</a>)
 * found here
 * <a href="https://www.gnu.org/licenses/lgpl-3.0.html">...</a> and copied below the license file.
 * <p>
 * OnsieaLogger is libre software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3.0 of the License, or (at your option)
 * any later version.
 * <p>
 * OnsieaLogger is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with OnsieaLogger. If not, see <<a
 * href="https://www.gnu.org/lic<a href="enses/">...</a>>"><https://www.gnu.org/lice</a>nses/lgpl-3.0.html>.
 * <p>
 * Neither the name "Onsiea", "OnsieaLogger", or any derivative name or the names of its authors / contributors may be
 * used to endorse or promote products derived from this software and even less to name another project or other work
 * without clear and precise permissions written in advance.
 * <p>
 * (more details on <a href="https://github.com/Onsiea/OnsieaLogger/wiki/License">...</a>)
 *
 * @author Seynax
 */

module fr.onsiea.logger {
	requires static lombok;

	exports fr.onsiea.logger;
	exports fr.onsiea.logger.tag;
	exports fr.onsiea.logger.utils;
}