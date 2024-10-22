package com.cruru.email.util;

public class EmailTemplate {

    public static String generateVerificationEmailContent(String verificationCode) {
        return """
               <div style='font-family: Arial, sans-serif; padding: 20px; background-color: #ffffff; border: 1px solid #e0e0e0; max-width: 600px; margin: 0 auto;'>
                   <div style='text-align: center; padding: 20px 0; background-color: #AA2298; color: white;'>
                       <h1 style='margin: 0;'>크루루</h1>
                   </div>
                   <div style='padding: 30px; text-align: center;'>
                       <h2 style='color: #333;'>[크루루] 인증 코드 안내</h2>
                       <p style='font-size: 16px; color: #555;'>안녕하세요,</p>
                       <p style='font-size: 16px; color: #555;'>아래 인증 코드를 입력해 주세요:</p>
                       <div style='padding: 20px; background-color: #f0f0f0; border-radius: 10px; display: inline-block; margin: 20px 0;'>
                           <span id='verificationCode' style='font-size: 32px; font-weight: bold; color: #333;'>%s</span>
                       </div>
                       <p style='font-size: 14px; color: #888;'>이 코드는 10분 후에 만료됩니다.</p>
                   </div>
                   <hr style='border: none; border-top: 1px solid #ddd; margin: 20px 0;'>
                   <div style='padding: 20px; text-align: center; font-size: 12px; color: #888;'>
                       <p>본 메일은 크루루 시스템에 의해 자동 발송되었습니다.</p>
                       <p>&copy; 2024 크루루. All Rights Reserved.</p>
                   </div>
               </div>
               """.formatted(verificationCode);
    }
}
