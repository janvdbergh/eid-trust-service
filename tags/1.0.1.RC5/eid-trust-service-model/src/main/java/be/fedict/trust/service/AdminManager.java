/*
 * eID Trust Service Project.
 * Copyright (C) 2009-2010 FedICT.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version
 * 3.0 as published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, see 
 * http://www.gnu.org/licenses/.
 */

package be.fedict.trust.service;

import java.security.cert.X509Certificate;

import javax.ejb.Local;

/**
 * Administrator manager.
 * 
 * @author wvdhaute
 */
@Local
public interface AdminManager {

	/**
	 * @param certificate
	 *            certificate
	 * @return whether the (already authenticated) admin identifier indeed
	 *         belongs to an admin.
	 */
	boolean isAdmin(X509Certificate certificate);
}
