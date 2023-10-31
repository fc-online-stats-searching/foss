package com.example.teamproject.feature.legacy // package com.example.teamproject.feature
//
// import android.os.Bundle
// import androidx.fragment.app.Fragment
// import android.view.LayoutInflater
// import android.view.View
// import android.view.ViewGroup
// import androidx.fragment.app.activityViewModels
// import com.example.teamproject.R
// import com.example.teamproject.databinding.FragmentPlayTypeBinding
//
// // TODO: Rename parameter arguments, choose names that matc
// class PlayTypeFragment : Fragment() {
//    val myViewModel: MyViewModel by activityViewModels()
//    lateinit var binding:FragmentPlayTypeBinding
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding=FragmentPlayTypeBinding.inflate(layoutInflater)
//        return binding!!.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//        myViewModel.curRank.observe(requireActivity()){
//            for(i in it){
//                if(i.matchType==50){
//                    when(i.division){
//                        800->{
//                            binding.imageView.setImageResource(R.drawable.eight)
//                        }
//                        900->{
//                            binding.imageView.setImageResource(R.drawable.nine)
//                        }
//                        1000->{
//                            binding.imageView.setImageResource(R.drawable.ten)
//                        }
//                        1100->{
//                            binding.imageView.setImageResource(R.drawable.eleven)
//                        }
//                        1200->{
//                            binding.imageView.setImageResource(R.drawable.twelve)
//                        }
//                        1300->{
//                            binding.imageView.setImageResource(R.drawable.thirteen)
//                        }
//                        2000->{
//                            binding.imageView.setImageResource(R.drawable.twenty)
//                        }
//                        2100->{
//                            binding.imageView.setImageResource(R.drawable.twentyone)
//                        }
//                        2200->{
//                            binding.imageView.setImageResource(R.drawable.twentytwo)
//                        }
//                        2300->{
//                            binding.imageView.setImageResource(R.drawable.twentythree)
//                        }
//                        2400->{
//                            binding.imageView.setImageResource(R.drawable.twentyfour)
//                        }
//                        2500->{
//                            binding.imageView.setImageResource(R.drawable.twentyfive)
//                        }
//                        2600->{
//                            binding.imageView.setImageResource(R.drawable.twentysix)
//                        }
//                        2700->{
//                            binding.imageView.setImageResource(R.drawable.twentyseven)
//                        }
//                        2800->{
//                            binding.imageView.setImageResource(R.drawable.twentyeight)
//                        }
//                        2900->{
//                            binding.imageView.setImageResource(R.drawable.twentynine)
//                        }
//                        3000->{
//                            binding.imageView.setImageResource(R.drawable.thirty)
//                        }
//                        3100->{
//                            binding.imageView.setImageResource(R.drawable.thirtyone)
//                        }
//                    }
//                }
//
//            }
//        }
//
//        myViewModel.curMatchResponse.observe(requireActivity()){
//            var shootInBox: Float= 0.0F
//            var shootTotal: Float= 0.0F
//            var shootOutPenalty: Float= 0.0F
//            var longPass: Float= 0.0F
//            var shortPass: Float= 0.0F
//            var throughPass:Float= 0.0F
//            for (i in myViewModel.curMatchResponse.value!!) {
//                for (j in i.matchInfoResponse) {
//                    if (myViewModel.UserId.value == j.nickname) {
//                        shootInBox += j.shootResponse.shootInPenalty
//                        shootTotal += j.shootResponse.shootTotal
//                        shootOutPenalty += j.shootResponse.shootOutPenalty
//                        longPass += j.passResponse.longPassTry
//                        shortPass += j.passResponse.shortPassTry
//                        throughPass+=j.passResponse.lobbedThroughPassTry + j.passResponse.throughPassTry
//                    }
//                }
//            }
//            shootInBox/=30
//            shootTotal/=30
//            shootOutPenalty/=30
//            longPass/=30
//            shortPass/=30
//            throughPass/=30
//
//            binding.nickName.text=myViewModel.UserId.value.toString()
//            binding.boxShoot.text=String.format("%.1f", shootInBox)
//            binding.longShoot.text=String.format("%.1f", shootOutPenalty)
//            binding.shortPass.text=String.format("%.1f", shortPass)
//            binding.avgShoot.text=String.format("%.1f", shootTotal)
//            binding.longPass.text=String.format("%.1f", longPass)
//            binding.throughPass.text=String.format("%.1f", throughPass)
//        }
//
//    }
//
// }
