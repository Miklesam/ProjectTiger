package com.miklesam.dotamanager

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.games.AchievementsClient
import com.google.android.gms.games.Games
import com.google.android.gms.games.LeaderboardsClient
import com.miklesam.dotamanager.adapters.MarketPlayerHolder
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.simplefragments.*
import com.miklesam.dotamanager.ui.choosePlayers.FragmentChoosePlayers
import com.miklesam.dotamanager.ui.game.FragmentGame
import com.miklesam.dotamanager.ui.market.FragmentMarket
import com.miklesam.dotamanager.ui.team.FragmentTeam
import com.miklesam.dotamanager.ui.teams.FragmentTeams
import com.miklesam.dotamanager.ui.teamsprofile.FragmentTeamsProfile
import com.miklesam.dotamanager.utils.DetailsTransition


class MainActivity : AppCompatActivity(), FragmentMenu.MenuListener, FragmentLobby.LobbyListener,
    FragmentMarket.playerChoose, FragmentGame.backToLobby, FragmentNewGame.startListener,
    FragmentDescription.nextListener, FragmentChoosePlayers.nextChoosenListener,
    FragmentTeams.teamShow, FragmentTeamSigning.gotoLobby {

    private var googleSignInClient: GoogleSignInClient? = null
    private var achievementClient: AchievementsClient? = null
    private var leaderboardsClient: LeaderboardsClient? = null

    fun initGoogleClientAndSignin() {
        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN
            ).build()
        )

        googleSignInClient?.silentSignIn()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.w("Activity", "succes Sign")
                achievementClient = Games.getAchievementsClient(
                    this,
                    task.result!!
                )
                leaderboardsClient = Games.getLeaderboardsClient(
                    this,
                    task.result!!
                )
            } else {
                Log.e("Error", "signInError", task.exception)
            }
        }
    }

    fun showAchievements() {
        achievementClient?.achievementsIntent?.addOnSuccessListener { intent ->
            Log.w("Activity", "start Activity")
            startActivityForResult(intent, 0)
        }
    }

    //fun showTopPlayers() {
    //    leaderboardsClient?.allLeaderboardsIntent?.addOnSuccessListener {intent ->
    //        startActivityForResult(intent, 0)
    //    }
    //}


    override fun gameClicked() {
        showGame()
    }

    override fun marketClicked() {
        showMarket()
    }

    override fun trainingClicked() {
        showTrainning()
    }

    override fun teamClicked() {
        showTeam()
    }

    override fun newGameClicked() {
        showNewGame()
        //showLobby()
    }

    override fun contunueClicked() {
        achievementClient?.unlock(getString(R.string.achievement_1))
        showLobby()
    }

    override fun achievementsClicked() {
        showAchievements()
    }

    override fun teamsClicked() {
        showTeams()
    }

    override fun aboutClicked() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )



        if (savedInstanceState == null) {
            showFragmentMain()
        }

        initGoogleClientAndSignin()
    }


    private fun showFragmentMain() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentMenu()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    private fun showGame() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentGame(this)
        transaction.setCustomAnimations(
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left,
            R.anim.enter_left_to_right,
            R.anim.exit_left_to_right
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showMarket() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentMarket()
        transaction.setCustomAnimations(
            R.anim.enter_left_to_right,
            R.anim.exit_left_to_right,
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showTeam() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentTeam()
        transaction.setCustomAnimations(
            R.anim.enter_top_to_bottom,
            R.anim.exit_top_to_bottom,
            R.anim.enter_bottom_to_top,
            R.anim.exit_bottom_to_top
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showTrainning() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentMedia()
        transaction.setCustomAnimations(
            R.anim.enter_bottom_to_top,
            R.anim.exit_bottom_to_top,
            R.anim.enter_top_to_bottom,
            R.anim.exit_top_to_bottom
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showLobby() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentLobby()
        transaction.setCustomAnimations(
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left,
            R.anim.enter_left_to_right,
            R.anim.exit_left_to_right
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showTeams() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentTeams()
        transaction.setCustomAnimations(
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left,
            R.anim.enter_left_to_right,
            R.anim.exit_left_to_right
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun showNewGame() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentNewGame()
        transaction.setCustomAnimations(
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left,
            R.anim.enter_left_to_right,
            R.anim.exit_left_to_right
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showNoBackStackLobby() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentLobby()
        transaction.setCustomAnimations(
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left,
            R.anim.enter_left_to_right,
            R.anim.exit_left_to_right
        )
        transaction.replace(R.id.fragment_holder, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onPlayerClickFragment(player: Player, holder: MarketPlayerHolder) {
        val fragment =
            FragmentPlayerProfile()
        fragment.setSharedElementEnterTransition(DetailsTransition())
        //val fade = Fade()
        //fragment.enterTransition=fade
        //fragment.exitTransition=fade
        //exitTransition = fade
        fragment.setSharedElementReturnTransition(DetailsTransition())
        val transaction =
            supportFragmentManager.beginTransaction()
                .addSharedElement(holder.photo, "playerImage")
                .addSharedElement(holder.name, "signature1")
                .addSharedElement(holder.position, "signature2")
                .addSharedElement(holder.cost, "signature3")
        //transaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right)
        val bundle = Bundle()
        bundle.putParcelable("player", player)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun backToLobbyCLicked() {
        Log.w("Activity", "onEnd Clicked")
        //showNoBackStackLobby()
        onBackPressed()
    }

    override fun startClickedClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentDescription()
        transaction.setCustomAnimations(
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left,
            R.anim.enter_left_to_right,
            R.anim.exit_left_to_right
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    override fun nextClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentChoosePlayers()
        transaction.setCustomAnimations(
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left,
            R.anim.enter_left_to_right,
            R.anim.exit_left_to_right
        )
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    override fun nextChoosenClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentTeamSigning()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()

    }

    override fun teamsClicked(teamsNickNames: ArrayList<String?>, teamName: String) {
        showTeamProfile(teamsNickNames, teamName)
    }

    fun showTeamProfile(teamsNickNames: ArrayList<String?>, teamName: String) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentTeamsProfile()
        val bundle = Bundle()
        bundle.putStringArrayList("players", teamsNickNames)
        bundle.putString("teamName", teamName)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun toLobby() {
        showNoBackStackLobby()
    }


}
