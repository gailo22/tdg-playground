package com.gailo22.chatapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.jivesoftware.smack.*
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.DomainBareJid
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.Jid
import org.jxmpp.jid.impl.JidCreate
import org.jxmpp.stringprep.XmppStringprepException
import java.io.IOException
import java.net.InetAddress


class LoginActivity : AppCompatActivity() {

    private val LOGTAG = "CHAT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button =  findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            Log.d(LOGTAG,"User clicked on login")

            val runnable = Runnable {
                var connectionConfiguration: XMPPTCPConnectionConfiguration? = null

                try {
//                    Jid jid = JidCreatefrom("gailo22@chinwag.im/balcony")
//                    val domainBareFrom = JidCreate.domainBareFrom("xmpp.chinwag.im")

//                    connectionConfiguration = XMPPTCPConnectionConfiguration.builder()
//                        .setHost("chat.chinwag.im")
//                        .setUsernameAndPassword("gailo22", "password")
//                        .setXmppDomain("chinwag.im")
//                        .setKeystoreType(null)
//                        .build()

                    connectionConfiguration = XMPPTCPConnectionConfiguration.builder()
                        .setHost("mylocal.im")
                        .setXmppDomain(JidCreate.domainBareFrom("localhost"))
                        .setUsernameAndPassword("john", "password")
                        .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
//                        .setDebuggerEnabled(true)
                        .build();

                } catch (e: XmppStringprepException) {
                    e.printStackTrace()
                }

                val connection: AbstractXMPPConnection = XMPPTCPConnection(connectionConfiguration)

                connection.addConnectionListener(object : ConnectionListener {
                    override fun connected(connection: XMPPConnection) {
                        Log.d(LOGTAG, "CHAT - Connected")
                    }

                    override fun authenticated(connection: XMPPConnection, resumed: Boolean) {
                        Log.d(LOGTAG, "CHAT - Authenticated")
                    }

                    override fun connectionClosed() {
                        Log.d(LOGTAG, "CHAT - connectionClosed")
                    }

                    override fun connectionClosedOnError(e: Exception) {
                        Log.d(LOGTAG, "CHAT - connectionClosedOnError")
                    }

//                    override fun reconnectionSuccessful() {
//                        Log.d(LOGTAG, "CHAT - ReconnectionSuccessful")
//                    }
//
//                    override fun reconnectingIn(seconds: Int) {
//                        Log.d(LOGTAG, "CHAT - ReconnectingIn")
//                    }
//
//                    override fun reconnectionFailed(e: Exception) {
//                        Log.d(LOGTAG, "CHAT - ReconnectionFailed")
//                    }
                })

                val chatManager: ChatManager = ChatManager.getInstanceFor(connection)
                chatManager.addIncomingListener(IncomingChatMessageListener { from, message, chat ->
                    Log.d(LOGTAG, "Incomming message : " + message.body)
                })

                try {
                    connection.connect().login()
                } catch (e: XMPPException) {
                    e.printStackTrace()
                } catch (e: SmackException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            Thread(runnable).start()
        }
    }
}