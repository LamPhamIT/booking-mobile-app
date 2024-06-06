//package com.example.tanlam.test
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.google.gson.Gson
//import com.google.gson.JsonObject
//import okhttp3.FormBody
//import okhttp3.OkHttpClient
//import java.io.IOException
//import java.net.URLEncoder
//import java.nio.charset.StandardCharsets
//import kotlin.random.Random
//
//@Throws(ServletException::class, IOException::class)
//fun doPost() {
//    val vnp_Version = "2.1.0"
//    val vnp_Command = "pay"
//    val vnp_OrderInfo: String = "vnp_OrderInfo"
//    val orderType: String = "bill payment"
//    val vnp_TxnRef: String = Random.nextInt(100).toString()
//    val vnp_IpAddr: String = "127.0.0.1"
//    val vnp_TmnCode: String = "1DY0NL22"
//
//    val amount: Int = 10000 * 100
//    val vnp_Params: MutableMap<String, Any> = HashMap()
//    vnp_Params["vnp_Version"] = vnp_Version
//    vnp_Params["vnp_Command"] = vnp_Command
//    vnp_Params["vnp_TmnCode"] = vnp_TmnCode
//    vnp_Params["vnp_Amount"] = amount.toString()
//    vnp_Params["vnp_CurrCode"] = "VND"
//    val bank_code: String = "NCB"
//    if (bank_code != null && !bank_code.isEmpty()) {
//        vnp_Params["vnp_BankCode"] = bank_code
//    }
//    vnp_Params["vnp_TxnRef"] = vnp_TxnRef
//    vnp_Params["vnp_OrderInfo"] = vnp_OrderInfo
//    vnp_Params["vnp_OrderType"] = orderType
//
//    val locate: String = "vn"
////    if (locate != null && !locate.isEmpty()) {
////        vnp_Params["vnp_Locale"] = locate
////    } else {
////        vnp_Params["vnp_Locale"] = "vn"
////    }
//    vnp_Params["vnp_ReturnUrl"] = Config.vnp_Returnurl
//    vnp_Params["vnp_IpAddr"] = vnp_IpAddr
//    val cld: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"))
//
//    val formatter: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
//    val vnp_CreateDate: String = formatter.format(cld.getTime())
//
//    vnp_Params["vnp_CreateDate"] = vnp_CreateDate
//    cld.add(Calendar.MINUTE, 15)
//    val vnp_ExpireDate: String = formatter.format(cld.getTime())
//    //Add Params of 2.1.0 Version
//    vnp_Params["vnp_ExpireDate"] = vnp_ExpireDate
//    //Billing
//    vnp_Params["vnp_Bill_Mobile"] = req.getParameter("txt_billing_mobile")
//    vnp_Params["vnp_Bill_Email"] = req.getParameter("txt_billing_email")
//    val fullName: String = req.getParameter("txt_billing_fullname").trim()
//    if (fullName != null && !fullName.isEmpty()) {
//        val idx = fullName.indexOf(' ')
//        val firstName = fullName.substring(0, idx)
//        val lastName = fullName.substring(fullName.lastIndexOf(' ') + 1)
//        vnp_Params["vnp_Bill_FirstName"] = firstName
//        vnp_Params["vnp_Bill_LastName"] = lastName
//    }
//    vnp_Params["vnp_Bill_Address"] = req.getParameter("txt_inv_addr1")
//    vnp_Params["vnp_Bill_City"] = req.getParameter("txt_bill_city")
//    vnp_Params["vnp_Bill_Country"] = req.getParameter("txt_bill_country")
//    if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state")
//            .isEmpty()
//    ) {
//        vnp_Params["vnp_Bill_State"] = req.getParameter("txt_bill_state")
//    }
//    // Invoice
//    vnp_Params["vnp_Inv_Phone"] = req.getParameter("txt_inv_mobile")
//    vnp_Params["vnp_Inv_Email"] = req.getParameter("txt_inv_email")
//    vnp_Params["vnp_Inv_Customer"] = req.getParameter("txt_inv_customer")
//    vnp_Params["vnp_Inv_Address"] = req.getParameter("txt_inv_addr1")
//    vnp_Params["vnp_Inv_Company"] = req.getParameter("txt_inv_company")
//    vnp_Params["vnp_Inv_Taxcode"] = req.getParameter("txt_inv_taxcode")
//    vnp_Params["vnp_Inv_Type"] = req.getParameter("cbo_inv_type")
//    //Build data to hash and querystring
//    val fieldNames: List<*> = ArrayList(vnp_Params.keys)
//    Collections.sort(fieldNames)
//    val hashData = StringBuilder()
//    val query = StringBuilder()
//    val itr = fieldNames.iterator()
//    while (itr.hasNext()) {
//        val fieldName = itr.next() as String
//        val fieldValue = vnp_Params[fieldName] as String?
//        if ((fieldValue != null) && (fieldValue.length > 0)) {
//            //Build hash data
//            hashData.append(fieldName)
//            hashData.append('=')
//            hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()))
//            //Build query
//            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
//            query.append('=')
//            query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()))
//            if (itr.hasNext()) {
//                query.append('&')
//                hashData.append('&')
//            }
//        }
//    }
//    var queryUrl = query.toString()
//    val vnp_SecureHash: String = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString())
//    queryUrl += "&vnp_SecureHash=$vnp_SecureHash"
//    val paymentUrl: String = Config.vnp_PayUrl + "?" + queryUrl
//    val job: JsonObject = JsonObject()
//    job.addProperty("code", "00")
//    job.addProperty("message", "success")
//    job.addProperty("data", paymentUrl)
//    val gson = Gson()
//    resp.getWriter().write(gson.toJson(job))
//}