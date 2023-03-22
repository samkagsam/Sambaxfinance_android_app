package com.sambaxfinance.sambax.models

data class ApplyRequestModel(
    val first_name: String,
    val middle_name: String,
    val last_name: String,
    val gender: String,
    val date_of_birth: String,
    val home_address: String,
    val work_address: String,
    val nature_of_business: String,
    val contact_one: Int,
    val contact_two: Int,
    val requested_loan_amount: Int,
    val guarantor_one: String,
    val guarantor_one_contact: Int,
    val guarantor_one_relationship: String,
    val guarantor_two: String,
    val guarantor_two_contact: Int,
    val guarantor_two_relationship: String,
    val customer_id_url: String,
    val customer_image_url: String,
    val purpose_for_loan: String

)
