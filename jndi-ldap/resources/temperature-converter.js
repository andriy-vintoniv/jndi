function convert(temperature, degree) {
	var result;
	if (degree == "C") {
		result = temperature * 9 / 5 + 32;
	} else {
		result = (temperature - 32) * 5 / 9;
	}
	return result;
}