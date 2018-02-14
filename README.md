# iOS Style Date time picker
Date time picker will appear from bottom in any activity.


<img src="http://kkrmobility.com/data/project_gif/iOS_date_picker.gif"/>

<h2>Add it in your root build.gradle at the end of repositories:</h2>
<PRE><b>allprojects { 
      repositories {
			...
			maven { url 'https://jitpack.io' }
		} 
	}
</b>
</pre>

<br/>
<h2> Add the dependency </h2>
<pre>
dependencies {
	        compile 'com.github.krishnakantgupta:Date_time_picker_iOS_style:1.0.1'
	}
</pre>

<br/>
<h2><b> How to use Date Time picker in activity <b></h2>
	
	
	// Create new object on picker
	IOSStyleSateTimePicker bottomSheetSpinnerDateTimePicker = new IOSStyleSateTimePicker(WelcomeActivity.this, new Date()); 
	
	// Set date select listener
	bottomSheetSpinnerDateTimePicker.setDateTimeSelectListener(new OnDateTimeSelectListener() { 
	    @Override 
	    public void onDateTimeSet(Date date) { 

	    } 
	}); 
	
	// set date change listener
	bottomSheetSpinnerDateTimePicker.setDateTimeChangeListener(new OnDateTimeSelectListener() { 
	    @Override 
	    public void onDateTimeSet(Date date) { 

	    } 
	}); 
	
	// required only if you want to display date in specific formate.
	bottomSheetSpinnerDateTimePicker.setDateDisplayPattern("dd:mmm:yy HH"); 
	
	// Show dateTime picker
	bottomSheetSpinnerDateTimePicker.show();
