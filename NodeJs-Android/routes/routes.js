
var mongodb = require('mongodb');
var ObjectID = mongodb.ObjectID;

//users
var login = require('config/login');
var register =require('config/register');
var getuser =require('config/getuser');
var updateuser = require('config/updateuser');
var changepassword = require('config/changepassword');
var updateemail = require('config/updateemail');
var changephone = require('config/updatephone');
var deleteuser = require('config/deleteuser');
var updateemailuser = require('config/updateemailuser');
//events
var getevents = require('config/getevents');
var geteventemail = require('config/geteventsemail');
var geteventid = require('config/geteventid');
var addevent = require('config/addevent');
var updateevent = require('config/updateevent');
var deleteevent = require('config/deleteevent');
var getdetailsevent = require('config/getdetailsevent');

//sessions
var addsession =require('config/addsession');
var getsession = require('config/getsessions');
var deletesession = require('config/deletesession');
//booking
var getbookings = require('config/getbooking');
var getbookingid = require('config/getbookingid');
var deletebooking = require('config/deletebooking');

module.exports = function(app) {



app.get('/', function(request, response) {

		response.end("NodeJs-Android"); 
	});

//user


app.post('/login',function(request,response,next){
		
		var userinfo = request.body; // get post param
  		var userPassword = userinfo.password; //get password from post params
  	    var email = userinfo.email;
		
		login.login(email,userPassword,function (found) {
			console.log(found);
			response.json(found.response);
	    });
	});

app.post('/register',function(request,response,next){
		
		var userinfo = request.body; // get post param
  		var email = userinfo.email;	
  		var password = userinfo.password; //get password from post params
  		var name = userinfo.name;
		
		register.register(email,password,name,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});

app.get('/getuser/:email',function(request,response,next){
     	
     	var email = request.params.email;
		
		getuser.getuser(email,function (found) {
			console.log(found.response);
			response.json(found.response);
	    });		
	});
    
app.get('/getpassword/:email',function(request,response,next){
     	
     	var email = request.params.email;
		
		getpassword.getpassword(email,function (found) {
			console.log(found.response);
			response.json(found.response);
	    });		
	});

app.put('/updateuser/:email',function(request,response,next){
     	
     	var email = request.params.email;
     	var userinfo = request.body;
        var name = userinfo.name; 
        var info = userinfo.info;
        var adress = userinfo.adress;
        var tel = userinfo.tel;
        var linkdin = userinfo.linkdin;

		updateuser.updateuser(email,name,info,adress,tel,linkdin,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});

app.put('/updateemail/:email',function(request,response,next){
     	
     	var email = request.params.email;
     	var userinfo = request.body;
        var email2 = userinfo.email2; 
        
		updateemail.updateemail(email,email2,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});


app.put('/updateemailuser/:id',function(request,response,next){
     	
     
     	var id = new ObjectID(request.params.id);
     	var userinfo = request.body;
        var email = userinfo.email; 
        
		updateemail.updateemail(id,email,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});

app.put('/changepassword/:email',function(request,response,next){
     	
     	var email = request.params.email;
     	var userinfo = request.body;
        var newpassword = userinfo.newpassword; 
        var currentpassword = userinfo.currentpassword;
		
		changepassword.changepassword(currentpassword,newpassword,email,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});
    
app.put('/changephone/:email',function(request,response,next){
     	
     	var email = request.params.email;
     	var userinfo = request.body;
        var password = userinfo.password; 
        var phone = userinfo.phone;
		
		changephone.changephone(password,phone,email,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});


app.delete('/deleteuser/:email',function(request,response,next){
     	
     	var email = request.params.email;
        
        deleteuser.deleteuser(email,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});

 //events

app.get('/getevents',function(request,response,next){
		
		getevents.getevent(function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});
    
app.get('/geteventemail/:email',function(request,response,next){
     	
     	var email = request.params.email;
		
		geteventemail.geteventemail(email,function (found) {
			console.log(found.response);
			response.json(found.response);
	    });		
	});

app.get('/geteventid/:id',function(request,response,next){
     	
     	var id = new ObjectID(request.params.id);
		
		geteventid.geteventid(id,function (found) {
			console.log(found.response);
			response.json(found.response);
	    });		
	});


app.get('/getdetailsevent/:id',function(request,response,next){
     	
     	var id = new ObjectID(request.params.id);
		
		getdetailsevent.getdetailsevent(id,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});

app.delete('/deleteevent/:id',function(request,response,next){
     	
     	var id = new ObjectID(request.params.id);
		
		deleteevent.deleteevent(id,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});

app.put('/updateevent/:id',function(request,response,next){
     	
     	var id = new ObjectID(request.params.id);
     	var eventinfo = request.body;
        var email = eventinfo.email; 
        var titre = eventinfo.titre; 
        var date = eventinfo.date; 
        var heuredeb = eventinfo.heuredeb; 
        var heurefin = eventinfo.heurefin; 
        var lieu = eventinfo.lieu;
        var responsable = eventinfo.responsable;
        var description = eventinfo.description;

		updateevent.updateevent(id,email,titre,date,heuredeb,heurefin,lieu,responsable,description,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});

app.post('/addevent',function(request,response,next){
		
		var eventinfo = request.body;
        var email = eventinfo.email; 
        var titre = eventinfo.titre; 
        var date = eventinfo.date; 
        var heuredeb = eventinfo.heuredeb; 
        var heurefin = eventinfo.heurefin; 
        var lieu = eventinfo.lieu;
        var responsable = eventinfo.responsable;
        var description = eventinfo.description;

		addevent.addevent(email,titre,date,heuredeb,heurefin,lieu,responsable,description,function (found) {
			console.log(found);
			response.json(found);
	    });		
	});


//sessions
app.post('/addsession/:email',function(request,response,next){
		
		var email = request.params.email;
		var sessioninfo = request.body;
        var ip = sessioninfo.ip; 
        var localisation = sessioninfo.localisation; 
        var iemi = sessioninfo.iemi; 
        
		addsession.addsession(email,ip,localisation,iemi,function (found) {
			console.log(found);
			response.json(found);
	    });		
	});

app.get('/getsession/:email',function(request,response,next){
     	
     	var email = request.params.email;
		
		getsession.getsession(email,function (found) {
			console.log(found.response);
			response.json(found.response);
	    });		
	});

app.delete('/deletesession/:id',function(request,response,next){
     	
     	var id = new ObjectID(request.params.id);
		
		deletesession.deletesession(id,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});


//Booking

 app.get('/getbookings',function(request,response,next){
		getbookings.getbookings(function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});

  app.get('/getbookingid/:id',function(request,response,next){
     	var id = new ObjectID(request.params.id);
		getbookingid.getbookingid(id,function (found) {
			console.log(found.response);
			response.json(found.response);
	    });		
	});


  app.delete('/deletebooking/:id',function(request,response,next){
     	var id = new ObjectID(request.params.id);
		deletebooking.deletebooking(id,function (found) {
			console.log(found);
			response.json(found.response);
	    });		
	});
    
};	