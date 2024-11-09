package com.android.practiceapplication

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.practiceapplication.Service.ServiceLearning
import com.android.practiceapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private  val TAG = "FirstFragment"
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var serviceIntent:Intent
    private var isBound = false
    private lateinit var mService: ServiceLearning

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            val binder = service as ServiceLearning.LocalBinder
            mService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
            isBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        serviceIntent = Intent(requireContext(), ServiceLearning::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        binding.buttonStartService.setOnClickListener {
            requireContext().startService(serviceIntent)
        }


        binding.buttonBindService.setOnClickListener {
            requireContext().bindService(serviceIntent, connection, BIND_AUTO_CREATE)
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonAccessObject.setOnClickListener {
            if(isBound){
                Log.d(TAG, "onViewCreated: ${mService.getRandomNumber()}")
            }
        }


        binding.buttonUnbindService.setOnClickListener {
            requireContext().unbindService(connection)
            isBound=false
        }

        binding.buttonStopService.setOnClickListener {
            requireContext().stopService(serviceIntent)
        }

    }

    override fun onResume() {
        super.onResume()
        if(!isBound){
            requireContext().bindService(serviceIntent, connection, BIND_AUTO_CREATE)
        }
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        if(isBound){
            requireContext().unbindService(connection)
            isBound=false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
        if(isBound){
            requireContext().unbindService(connection)
            isBound=false
        }
        requireContext().stopService(serviceIntent)
        _binding = null
    }
}