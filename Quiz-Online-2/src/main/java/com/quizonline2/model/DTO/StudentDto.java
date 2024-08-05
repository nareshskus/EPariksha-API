package com.quizonline2.model.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentDto {

	private String userName;
	private String studentName;
	private String mobileNo;
	private String email;
}
