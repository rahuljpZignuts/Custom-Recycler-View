# Custom-Recycler-View
Useful Library to manage recycler view with shimmer Effect , progress bar and swipe to refresh 

You can use it like : 

>       <com.rahulparmar.CustomRecyclerView
>        app:swipe_refresh="true"
>        app:shimmer_layout="@layout/sample_shimmer_layout"
>        app:shimmer_items="20"
>        app:layout_manager="vertical"
>        app:error_text="Something went wrong"
>        app:refresh_button_text="Do Refresh"
>        app:empty_text="No Data Found" />
        
        
        
# Methods for Use Shimmer Loading : 
     startShimmer() // start shimmer effect
     stopShimmer() // stop shimmer effect
     validateRecyclerViewData(size : Int) // to display empty text according to list size\n
     showError() or showError(error : String) //display error message with refresh button\n

# Methods for Use ProgressBar Loading : 
     startProgress() // start shimmer effect
     stopProgress() // stop shimmer effect
     validateRecyclerViewData(size : Int) // to display empty text according to list size\n
     showError() or showError(error : String) //display error message with refresh button\n


implement and set listener CustomRecyclerView.RecyclerViewEventListener

interface methods ----{
-         fun onRefreshButtonClicked()
-         fun onSwipeRefresh()
    }


[![](https://jitpack.io/v/rahuljpZignuts/Custom-Recycler-View.svg)](https://jitpack.io/#rahuljpZignuts/Custom-Recycler-View)

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.rahuljpZignuts:Custom-Recycler-View:Tag'
	}
  
  That's it...
