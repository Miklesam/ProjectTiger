package com.miklesam.dotamanager

import android.content.Intent
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
import com.miklesam.dotamanager.dialogs.MessageDialog
import com.miklesam.dotamanager.simplefragments.*
import com.miklesam.dotamanager.ui.choosePlayers.FragmentChoosePlayers
import com.miklesam.dotamanager.ui.closedquali.ClosedQuali
import com.miklesam.dotamanager.ui.closedquali.ClosedRepository
import com.miklesam.dotamanager.ui.game.FragmentGame
import com.miklesam.dotamanager.ui.market.FragmentMarket
import com.miklesam.dotamanager.ui.minortournament.Minor
import com.miklesam.dotamanager.ui.pickstage.PickStage
import com.miklesam.dotamanager.ui.plainingstage.PlainingStage
import com.miklesam.dotamanager.ui.practice.FragmentPractice
import com.miklesam.dotamanager.ui.prematch.PreMatch
import com.miklesam.dotamanager.ui.prematch.PreMatchRepo
import com.miklesam.dotamanager.ui.teams.FragmentTeams
import com.miklesam.dotamanager.ui.teamsprofile.FragmentTeamsProfile
import com.miklesam.dotamanager.utils.*
import kotlinx.coroutines.*

val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())


class MainActivity : AppCompatActivity(), FragmentMenu.MenuListener, FragmentLobby.LobbyListener,
    FragmentMarket.playerChoose, FragmentGame.backToLobby, FragmentNewGame.startListener,
    FragmentDescription.nextListener, FragmentChoosePlayers.nextChoosenListener,
    FragmentTeams.teamShow, FragmentTeamSigning.gotoLobby,
    FragmentPractice.PracticeListener, PickStage.nextFromPick, PlainingStage.nextFromPlaining,
    FragmentMedia.MediaListener, PreMatch.afterCalculate, ClosedQuali.ClosedQualListener,
    MessageDialog.OnDialogClickListener {

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

    override fun mediaClicked() {
        mediaTrainning()
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

    override fun multipleerClicked() {
        //replaceFragmentFromRightToLeft(FragmentMultipleer(), true)
        val intent = Intent(this, MultipleerActivity::class.java)
        startActivity(intent)
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
        replaceFragment(FragmentPractice())
    }

    private fun showMarket() {
        replaceFragmentFromLeftToRight(FragmentMarket())
    }

    private fun showTeam() {
        //replaceFragmentFromBottomToTop(FragmentTeam())
        //replaceFragmentFromBottomToTop(MinorQuali())
        replaceFragment(Minor())

    }

    private fun mediaTrainning() {
        //replaceFragmentFromTopToBottom(FragmentMedia())
        replaceFragmentFromTopToBottom(ClosedQuali())
    }

    private fun showLobby() {
        replaceFragment(FragmentLobby())
    }

    private fun showTeams() {
        replaceFragmentFromRightToLeft(FragmentTeams(), true)
    }

    private fun showNewGame() {
        replaceFragment(FragmentNewGame())
    }

    private fun showNoBackStackLobby() {
        replaceFragment(FragmentLobby())
    }

    override fun onPlayerClickFragment(player: Player, holder: MarketPlayerHolder) {
        val fragment =
            FragmentPlayerProfile()
        fragment.setSharedElementEnterTransition(DetailsTransition())
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
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()

    }

    override fun startClickedClicked() {
        replaceFragment(FragmentDescription())
    }

    override fun nextClicked() {
        replaceFragment(FragmentChoosePlayers())
    }

    override fun nextChoosenClicked() {
        replaceFragment(FragmentTeamSigning())

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

    override fun teamTrainingClicked() {
        replaceFragment(PreMatch())
    }

    override fun soloTrainingClicked() {
        supportFragmentManager.popBackStack()
    }

    override fun pickEnded(
        radiant: ArrayList<Int>,
        direPicks: ArrayList<Int>
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = PlainingStage()
        val bundle = Bundle()
        bundle.putIntegerArrayList("radiant", radiant)
        bundle.putIntegerArrayList("dire", direPicks)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun plainingEnded(
        heroes: ArrayList<Int>?,
        direHeroes: ArrayList<Int>?
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentGame(this)
        val bundle = Bundle()
        bundle.putIntegerArrayList("radiant", heroes)
        bundle.putIntegerArrayList("dire", direHeroes)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
        //replaceFragmentFromRightToLeft(FragmentGame(this),false)
    }

    override fun morfClicked() {
        replaceFragmentFromRightToLeft(MorfNews(), true)
    }

    override fun calculateTolobby() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
    }

    override fun playGame() {
        replaceFragment(PickStage())
    }

    override fun preMatchClicked() {
        replaceFragment(PreMatch())
    }

    override fun onPositiveButtonClicked() {
        scope.launch {
            ClosedRepository(application).nukeClosed()
            PreMatchRepo(application).nukeScore()
            PrefsHelper.write(PrefsHelper.CLOSED_QUALI_DAY, "1")
        }
        supportFragmentManager.popBackStack()
    }
}
