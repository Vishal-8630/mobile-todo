const { setGlobalOptions } = require("firebase-functions/v2");
const { onCall } = require("firebase-functions/v2/https");
const nodemailer = require("nodemailer");

setGlobalOptions({ maxInstances: 10 });

// Setup Gmail transporter
const transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "vishh8630@gmail.com",
    pass: "pjpplarpzshgzidg",
  },
});

// Cloud Function to send OTP
exports.sendOtpEmail = onCall(async (request) => {
  const email = request.data.email;
  const otp = request.data.otp;

  const mailOptions = {
    from: "vishh8630@gmail.com",
    to: email,
    subject: "Your OTP Code",
    text: `Your OTP is: ${otp}`,
  };

  try {
    await transporter.sendMail(mailOptions);
    return { success: true, message: "OTP sent successfully" };
  } catch (error) {
    console.error("Error sending email:", error);
    return { success: false, message: error.toString() };
  }
});

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