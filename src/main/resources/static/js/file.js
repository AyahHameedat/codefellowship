function myFunction() {
  var x = document.getElementById("myInput");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
}



function Buttontoggle()
{
  var t = document.getElementById("123");
  if(t.innerHTML=="Some Text"){
      t.innerHTML="Toggled Text";}
  else{
      t.innerHTML="Some Text";}
}