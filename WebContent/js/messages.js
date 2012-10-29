function validate(form) {
  var name = form.txtName.value;
  var lastName = form.txtLastName.value;
  var cedula = form.txtCedIdNum.value;
  var nameUser = form.txtUserName.value;
  var pass = form.txtPassword.value;
  var passRpt = form.txtPasswordRpt.value;
  var rol = form.txtRoleId.value;  
  var sala = form.txtNumSal.value;   
  var products = form.elements["txtProductoId[]"];
  var nameRegex = /[\d\w\sáéíóú]+/i;
  var cedulaRegex = /^[0-9]+(([\.][0-9])?[0-9]*)*$/;
  
  if(name == "") {
    inlineMsg('txtName','Debe introducir el nombre del usuario.',2);
    return false;
  }
  if(!name.match(nameRegex)) {
    inlineMsg('name','Debe introducir un nombre valido.',2);
    return false;
  }
  if(lastName == "") {
    inlineMsg('txtLastName','Debe introducir el apellido del usuario.',2);
    return false;
  }
  if(!lastName.match(nameRegex)) {
    inlineMsg('email','Debe introducir un apellido de usuario valido.',2);
    return false;
  }
  if(cedula == "") {
    inlineMsg('txtCedIdNum','Debe introducir la c&eacute;dula del usuario.',2);
    return false;
  }
  if(!cedula.match(cedulaRegex)) {
    inlineMsg('txtCedIdNum','Debe introducir un n&uacute;mero de c&eacute;dula correcto.',2);
    return false;
  }
  if(nameUser == "") {
    inlineMsg('txtUserName','Debe introducir el nombre de usuario.', 2);
    return false;
  }
  if(pass == "") {
    inlineMsg('txtPassword','Debe introducir la contrase&ntilde;a del usuario.', 2);
    return false;
  }
  if(passRpt == "") {
    inlineMsg('txtPasswordRpt','Debe repetir la contrase&ntilde;a del usuario.', 2);
    return false;
  }
  if (pass.length < 6){
	  inlineMsg('txtPassword','Las contraseña debe tener una longitud mayor o igual a 6 caracteres.', 2);
	  return false;
  }
  if(passRpt != pass) {
    inlineMsg('txtPasswordRpt','Las contraseñas deben coincidir', 2);
    return false;
  }
  if(rol == "-1") {
    inlineMsg('txtRoleId','Debe escoger el rol del usuario.', 2);
    return false;
  }
  if((sala == "0") && ((rol == "2") || (rol == "4") || (rol == "5") || (rol == "6"))) {
    inlineMsg('txtNumSal','Debe escoger la sala en la que se encuentra el usuario.');
    return false;
  }
  for (var x = 0; x < products.length; x ++) {
	if (products[x].checked) {
		return true;
	}
  }
  
  inlineMsg('txtProductoId','Debe seleccinar al menos un producto.', 2);
  return false;
}


function validateLogin(form) {
  var name = form.txtName.value;
  var pass = form.txtPassword.value;
 
  
  if(name == "") {
    inlineMsg('txtName','Debe introducir su nombre de usuario.',2);
    return false;
  }
 
  if(pass == "") {
    inlineMsg('txtPassword','Debe introducir su contrase&ntilde;a.', 2);
    return false;
  }
  
  return true;
}


function validateCreateProduct(form) {
	  var name = form.txtNameProduct.value;
	  var price = form.txtPrice.value;
	  
	  if(name == "") {
	    inlineMsg('txtNameProduct','Debe introducir el nombre del producto.',2);
	    return false;
	  }
	 
	  if(price == "") {
	    inlineMsg('txtPrice','Debe introducir el precio del producto.', 2);
	    return false;
	  }
	  
	  return true;
}

function validateJustification(form) {
	if (form.otherJustif.value == ""){
		alert('Debe introducir una justificación.');
		form.otherJustif.focus();
		return false;
	}
   return true;
}


function validatePayment(form) {
	  var cardType = form.txtCardType.value;
	  var numCard = form.numCard.value;
	  var bank = form.txtBank.value;
	  var ced = form.txtCedNumClient.value;
	  var voucher = form.txtVoucher.value;
		  
	  if(cardType == "") {
	    inlineMsg('txtCardType','Debe introducir el tipo de tarjeta del cliente.',2);
	    return false;
	  }
	   if(numCard == "") {
	    inlineMsg('numCard','Debe introducir el número de tarjeta del cliente.',2);
	    return false;
	  }
	   if(bank == "") {
	    inlineMsg('txtBank','Debe introducir el banco al que pertenece la tarjeta del cliente.',2);
	    return false;
	  }
	   if(ced == "") {
	    inlineMsg('txtCedNumClient','Debe introducir la cédula del cliente.',2);
	    return false;
	  }
	   
	  if(voucher == "") {
	    inlineMsg('txtVoucher','Debe introducir el número de voucher.',2);
	    return false;
	  }
	 
	  return true;
}



// START OF MESSAGE SCRIPT //

var MSGTIMER = 20;
var MSGSPEED = 5;
var MSGOFFSET = 3;
var MSGHIDE = 3;

// build out the divs, set attributes and call the fade function //
function inlineMsg(target,string,autohide) {
  var msg;
  var msgcontent;
  if(!document.getElementById('msg')) {
    msg = document.createElement('div');
    msg.id = 'msg';
    msgcontent = document.createElement('div');
    msgcontent.id = 'msgcontent';
    document.body.appendChild(msg);
    msg.appendChild(msgcontent);
    msg.style.filter = 'alpha(opacity=0)';
    msg.style.opacity = 0;
    msg.alpha = 0;
  } else {
    msg = document.getElementById('msg');
    msgcontent = document.getElementById('msgcontent');
  }
  msgcontent.innerHTML = string;
  msg.style.display = 'block';
  var msgheight = msg.offsetHeight;
  var targetdiv = document.getElementById(target);
  targetdiv.focus();
  var targetheight = targetdiv.offsetHeight;
  var targetwidth = targetdiv.offsetWidth;
  var topposition = topPosition(targetdiv) - ((msgheight - targetheight) / 2);
  var leftposition = leftPosition(targetdiv) + targetwidth + MSGOFFSET;
  msg.style.top = topposition + 'px';
  msg.style.left = leftposition + 'px';
  clearInterval(msg.timer);
  msg.timer = setInterval("fadeMsg(1)", MSGTIMER);
  if(!autohide) {
    autohide = MSGHIDE;  
  }
  window.setTimeout("hideMsg()", (autohide * 1000));
}

// hide the form alert //
function hideMsg(msg) {
  var msg = document.getElementById('msg');
  if(!msg.timer) {
    msg.timer = setInterval("fadeMsg(0)", MSGTIMER);
  }
}

// face the message box //
function fadeMsg(flag) {
  if(flag == null) {
    flag = 1;
  }
  var msg = document.getElementById('msg');
  var value;
  if(flag == 1) {
    value = msg.alpha + MSGSPEED;
  } else {
    value = msg.alpha - MSGSPEED;
  }
  msg.alpha = value;
  msg.style.opacity = (value / 100);
  msg.style.filter = 'alpha(opacity=' + value + ')';
  if(value >= 99) {
    clearInterval(msg.timer);
    msg.timer = null;
  } else if(value <= 1) {
    msg.style.display = "none";
    clearInterval(msg.timer);
  }
}

// calculate the position of the element in relation to the left of the browser //
function leftPosition(target) {
  var left = 0;
  if(target.offsetParent) {
    while(1) {
      left += target.offsetLeft;
      if(!target.offsetParent) {
        break;
      }
      target = target.offsetParent;
    }
  } else if(target.x) {
    left += target.x;
  }
  return left;
}

// calculate the position of the element in relation to the top of the browser window //
function topPosition(target) {
  var top = 0;
  if(target.offsetParent) {
    while(1) {
      top += target.offsetTop;
      if(!target.offsetParent) {
        break;
      }
      target = target.offsetParent;
    }
  } else if(target.y) {
    top += target.y;
  }
  return top;
}

// preload the arrow //
if(document.images) {
  arrow = new Image(7,80); 
  arrow.src = "./msg_arrow.gif"; 
}
	


