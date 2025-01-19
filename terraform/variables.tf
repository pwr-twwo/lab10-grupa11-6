# ----------------------------------------------------
# NETWORK vars
# ----------------------------------------------------

variable "be_ip_port" {
    type = string
    description = "backend's port"
}

# ----------------------------------------------------
# DB vars
# ----------------------------------------------------


variable "aws_rds_db" {
    type = string
    description = "db's name"
}

variable "db_user" {
    type = string
    description = "db's user username"
}

variable "db_password" {
    type = string
    description = "db's user password"
}
