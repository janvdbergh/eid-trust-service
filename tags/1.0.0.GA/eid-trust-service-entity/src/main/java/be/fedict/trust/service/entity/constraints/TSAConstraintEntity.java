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

package be.fedict.trust.service.entity.constraints;

import javax.persistence.Entity;

import be.fedict.trust.service.entity.TrustDomainEntity;

@Entity
public class TSAConstraintEntity extends CertificateConstraintEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public TSAConstraintEntity() {

		super();
	}

	/**
	 * Main constructor.
	 */
	public TSAConstraintEntity(TrustDomainEntity trustDomain) {

		super(trustDomain);
	}
}
