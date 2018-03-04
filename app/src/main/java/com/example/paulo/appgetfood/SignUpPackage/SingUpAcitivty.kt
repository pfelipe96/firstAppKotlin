package com.example.paulo.appgetfood.SignUpPackage
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.paulo.appgetfood.BR
import com.example.paulo.appgetfood.R
import com.example.paulo.appgetfood.SignUpPackage.SingUpViewModel.OnResponseSingUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.toolbar.*

class SingUpAcitivty : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mGetValueFirebase : OnResponseSingUp
    lateinit var mToolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mToolbar = toolbar
        mToolbar.title = getString(R.string.title_toolbar_singup)
        setSupportActionBar(mToolbar)

        mAuth = FirebaseAuth.getInstance()
        mAuthListener  = FirebaseAuth.AuthStateListener {
            var user : FirebaseUser = it.currentUser!!
            user?.let {
                Log.v("Entrou", "Entrou "+user.uid)
            }
        }

        val singUpViewModel = SingUpViewModel()

        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_sing_up_acitivty)
        binding.setVariable(BR.viewModel, singUpViewModel)

        mGetValueFirebase = object : SingUpViewModel.OnResponseSingUp{
            override fun sendAuthFirebaseCreateUser(email: String, password: String) {
               mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this@SingUpAcitivty, { task ->
                           if(task.isSuccessful){
                               Log.v("Sucess", "ok")
                           }else{
                               Log.v("Failed", "bad")
                           }
                       })
            }
        }

        singUpViewModel.initializeListener(mGetValueFirebase)


    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener { mAuthListener }
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener { mAuthListener }
    }
}
