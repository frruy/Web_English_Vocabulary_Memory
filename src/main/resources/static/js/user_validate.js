const passwordInput = document.getElementById('password');
const passwordRequirements = document.getElementById('.password-requirements');
const test = document.getElementById('test')

passwordInput.addEventListener('input', function () {
    const password = this.value;
    const hasLength = password.length >= 8;
    const hasUppercase = /[A-Z]/.test(password);
    const hasLowercase = /[a-z]/.test(password);
    const hasNumber = /\d/.test(password);

    if (hasLength && hasUppercase && hasLowercase && hasNumber) {
        passwordRequirements.style.color = '#007bff';
        test.style.color = '#007bff';
        test.title = 'HEEH';
        console.log("green")

    } else {
        passwordRequirements.style.color = '#fa0404';
        console.log("red")
    }
});
