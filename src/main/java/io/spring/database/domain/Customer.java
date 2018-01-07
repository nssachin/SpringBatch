package io.spring.database.domain;

import java.sql.Date;

public class Customer {
	private final long id1;
	private final String firstName;
	private final String lastName;
	private final Date birthDate;
	
	public Customer(long id, String firstName, String lastName, Date birthDate) {
		this.id1 = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id1 + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate
				+ "]";
	}
}
