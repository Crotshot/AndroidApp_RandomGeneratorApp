package wit.assignments.randomgeneratorapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import timber.log.Timber
import wit.assignments.randomgeneratorapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity(){
    companion object{
        private const val RC_SIGN_IN = 0
    }

    private lateinit var binding: ActivitySignInBinding
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var storage : FirebaseStorage
    private lateinit var storageRef : StorageReference
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        analytics = FirebaseAnalytics.getInstance(this)
        storage = FirebaseStorage.getInstance()
        storageRef = storage.getReference("/")
        auth = Firebase.auth

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val currentUser = auth.currentUser
        //updateUI(currentUser)
        super.onStart()
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Timber.i("firebaseAuthWithGoogle:%s", account.id)
                    if(account.idToken != null) {
                        firebaseAuthWithGoogle(account.idToken!!)
                    }
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Timber.i("Google sign in failed")
                }
            }
            else{
                Timber.i("Google sign in failed%s", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.i("signInWithCredential:success")

                    //var resultIntent : Intent = Intent()
                    //resultIntent.putExtra("Bags", bags)
                    setResult(AppCompatActivity.RESULT_OK)//, resultIntent)
                    finish()

//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.i("signInWithCredential:failure%s", task.exception)
//                    updateUI(null)
                }
            }
    }

}