import sys

if len(sys.argv) > 2:
    first_name = sys.argv[1]
    last_name = sys.argv[2]
    print(f"Credentials: First name {first_name}, Last name {last_name}")
else:
    print("Please provide first and last names")