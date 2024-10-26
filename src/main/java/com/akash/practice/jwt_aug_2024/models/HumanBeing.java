package com.akash.practice.jwt_aug_2024.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// import java.util.function.Function;
import java.util.stream.Collectors;

import javax.management.relation.RoleNotFoundException;

import org.slf4j.Logger;

import com.akash.practice.jwt_aug_2024.utilities.UniqueId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.akash.practice.jwt_aug_2024.utilities.Roles;

// import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class HumanBeing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int uniqueId;
	// @Size(min = 10, message = "Enter at least 10 letter name")
	private String name;
	private String email;
	// @JsonIgnore
	private String password;
	private short age;
	private String roles;
	private String dob;
	List<String> rolesList;

	// private String equalAnswer;

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<String> getRolesList() {
		return rolesList;
	}

	public void setRolesList() throws RoleNotFoundException {
		this.rolesList = this.getRolesFromString(this.roles);
	}

	List<String> getRolesFromString(String roles) throws RoleNotFoundException {
		roles = roles.toUpperCase();
		// System.out.println("Roles : " + roles);
		List<String> ans = new ArrayList<>();
		String[] array = roles.split(",");
		List<String> availableRolesList = Arrays.asList(Roles.values()).stream()
				.map(Enum::name).collect(Collectors.toList());

		if (array.length == 1) {
			if (availableRolesList.contains(array[0])) {
				ans.add(array[0]);
			} else {
				System.out.println(array[0] + " role is not defined ");
				throw new RoleNotFoundException(array[0] + " role is not defined ");
			}
		} else {
			for (String role : array) {
				if (availableRolesList.contains(role)) {
					if (!ans.contains(role)) {
						ans.add(role);
					} else {
						System.out.println("Duplicate role detected : " + role + ", is ignored.");
					}
				} else {
					System.out.println(role + " role is not defined ");
					throw new RoleNotFoundException(role + " role is not defined ");
				}
			}
		}
		return ans;
	}

	public HumanBeing(String name, String email, short age, String roles, String dob,
			String password) throws RoleNotFoundException {
		// Random random = new Random();
		// this.id = random.nextInt(100000);
		this.uniqueId = UniqueId.getNextUserCount();

		this.name = name;
		this.email = email;
		this.age = age;
		this.roles = roles;
		this.dob = dob;
		// this.id = id;
		this.password = password;
		this.rolesList = getRolesFromString(roles);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HumanBeing() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String name) {
		this.email = name;
	}

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	public String getRoles() {
		return roles;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [unique Id =" + uniqueId + ", name=" + email + ", age=" + age + ", role=" + roles + ", dob=" + dob
				+ "]";
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	// public String getEqualAnswer() {
	// return equalAnswer;
	// }

	// public void setEqualAnswer(String equalAnswer) {
	// System.err.println("saving equal string");
	// this.equalAnswer = equalAnswer;
	// }

	public void setValues(HumanBeing user) {
		// this.id = user.getId();
		// this.uniqueId = user.getUniqueId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.age = user.getAge();
		this.roles = user.getRoles();
		this.dob = user.getDob();
		// this.equalAnswer = "";
	}

	public String getMatchingString(HumanBeing thisUser, HumanBeing user) {
		List<String> matching = new ArrayList<>();
		List<String> notMatching = new ArrayList<>();

		boolean isNameMatching = thisUser.getName().equals(user.getName());
		if (isNameMatching)
			matching.add("Name");
		else
			notMatching.add("Name");

		boolean isEmailMatching = thisUser.getEmail().equals(user.getEmail());
		if (isEmailMatching)
			matching.add("Email");
		else
			notMatching.add("Email");

		boolean isPasswordMatching = thisUser.getPassword().equals(user.getPassword());
		if (isPasswordMatching)
			matching.add("Password");
		else
			notMatching.add("Password");

		boolean isAgeMatching = thisUser.getAge() == user.getAge();
		if (isAgeMatching)
			matching.add("Age");
		else
			notMatching.add("Age");

		boolean isRoleMatching = thisUser.getRoles().equals(user.getRoles());
		if (isRoleMatching)
			matching.add("Role");
		else
			notMatching.add("Role");

		boolean isDobMatching = thisUser.getDob().equals(user.getDob());
		if (isDobMatching)
			matching.add("Dob");
		else
			notMatching.add("Dob");

		StringBuilder matchingString = new StringBuilder("");
		if (matching.isEmpty()) {
			matchingString.append("All values are updated !");
		} else if (matching.size() == 1) {
			matchingString.append("Only " + matching.get(0) + " is matching.");
		} else {
			for (int i = 0; i < matching.size(); i++) {
				if (i == (matching.size() - 2)) {
					matchingString.append(matching.get(i));
				} else if (i == (matching.size() - 1)) {
					matchingString.append(" and ").append(matching.get(i));
				} else {
					matchingString.append(matching.get(i)).append(", ");
				}
			}
			matchingString.append(" are matching.");
		}
		// System.out.println("1. " + matchingString);

		StringBuilder notMatchingString = new StringBuilder("");
		if (notMatching.isEmpty()) {
			notMatchingString.append("All values are same !");
		} else if (notMatching.size() == 1) {
			notMatchingString.append("Only " + notMatching.get(0) + " is updated !");
		} else {
			for (int i = 0; i < notMatching.size(); i++) {
				if (i == (notMatching.size() - 2)) {
					notMatchingString.append(notMatching.get(i));
				} else if (i == (notMatching.size() - 1)) {
					notMatchingString.append(" and ").append(notMatching.get(i));
					// notMatchingString = notMatchingString + "and " + notMatching.get(i);
				} else {
					notMatchingString.append(notMatching.get(i)).append(", ");
					// notMatchingString = notMatchingString + notMatching.get(i);
				}
			}
			notMatchingString.append(" are updated !");
		}
		// System.out.println("2. " + notMatchingString);

		return matchingString.append("\n").append(notMatchingString).toString();

	}

	@Override
	public boolean equals(Object user) {
		try {
			HumanBeing tempBeing = (HumanBeing) user;
			boolean isNameMatching = this.name.equals(tempBeing.getName());
			boolean isEmailMatching = this.email.equals(tempBeing.getEmail());
			boolean isPasswordMatching = this.password.equals(tempBeing.getPassword());
			boolean isAgeMatching = this.age == tempBeing.getAge();
			boolean isRoleMatching = this.roles.equals(tempBeing.getRoles());
			boolean isDobMatching = this.dob.equals(tempBeing.getDob());

			if (isNameMatching && isEmailMatching && isPasswordMatching && isAgeMatching && isRoleMatching
					&& isDobMatching) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Can not convert object to human being");
		}
		System.out.println("Can not convert object to human being");
		return false;
	}

}
