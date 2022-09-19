window.addEventListener("load", function()
{
	if (window.location.search != '')
	{
		var urlp = new URLSearchParams(window.location.search);
		if (urlp.has('failed'))
		{
			document.getElementById('login-failed').hidden = false;
		}
		else if (urlp.has('auth'))
		{
			document.getElementById('session-timeout').hidden = false;
		}
	}
});

function toggle()
{
	var pwd = document.getElementById('password');
	var icon = document.getElementById('toggle-icon');
	if (pwd.type === 'password')
	{
		pwd.type = "text";
		icon.src = '/login-hpwd.png';
	}
	else
	{
		pwd.type = "password";
		icon.src = '/login-spwd.png';
	}
}