package com.mindmatrix.nammametro.data

/**
 * Visual step-by-step guide content (FR-02). Each step has a title, an
 * instruction body, and an illustrative drawable. Steps are generated from
 * the computed Route so first-time users see exactly what to do next.
 */
data class VisualStep(
    val titleEn: String,
    val titleKn: String,
    val instructionEn: String,
    val instructionKn: String,
    val drawableRes: Int,
)

object StepData {

    fun buildForRoute(route: Route): List<VisualStep> {
        val steps = mutableListOf<VisualStep>()
        val from = route.steps.first().station
        val to = route.steps.last().station
        val firstLine = from.line

        steps += VisualStep(
            titleEn = "1. Buy your token",
            titleKn = "೧. ಟೋಕನ್ ಖರೀದಿಸಿ",
            instructionEn = "Walk up to the token machine. Tap your destination \"${to.name}\". Insert money. Collect the round token.",
            instructionKn = "ಟೋಕನ್ ಯಂತ್ರಕ್ಕೆ ಹೋಗಿ. \"${to.nameKn}\" ಆಯ್ಕೆಮಾಡಿ. ಹಣ ಹಾಕಿ. ಟೋಕನ್ ತೆಗೆದುಕೊಳ್ಳಿ.",
            drawableRes = com.mindmatrix.nammametro.R.drawable.illustration_token
        )

        steps += VisualStep(
            titleEn = "2. Tap token at gate",
            titleKn = "೨. ಗೇಟ್‌ನಲ್ಲಿ ಟ್ಯಾಪ್ ಮಾಡಿ",
            instructionEn = "Tap the round token on the green circle at the entry gate. The doors open. Walk through.",
            instructionKn = "ಪ್ರವೇಶ ಗೇಟ್‌ನ ಹಸಿರು ವೃತ್ತದ ಮೇಲೆ ಟೋಕನ್ ಇಡಿ. ಬಾಗಿಲು ತೆರೆಯುತ್ತದೆ. ಒಳಗೆ ಹೋಗಿ.",
            drawableRes = com.mindmatrix.nammametro.R.drawable.illustration_gate
        )

        steps += VisualStep(
            titleEn = "3. Find ${firstLine.displayName} platform",
            titleKn = "೩. ${firstLine.displayName} ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ಹುಡುಕಿ",
            instructionEn = "Follow the ${firstLine.displayName.lowercase()} arrow on the floor. Take the escalator up.",
            instructionKn = "${firstLine.displayName} ಬಾಣದ ಗುರುತು ಅನುಸರಿಸಿ. ಮೆಟ್ಟಿಲು ಮೇಲೆ ಹೋಗಿ.",
            drawableRes = if (firstLine == Line.PURPLE) com.mindmatrix.nammametro.R.drawable.illustration_platform_purple
                else com.mindmatrix.nammametro.R.drawable.illustration_platform_green
        )

        steps += VisualStep(
            titleEn = "4. Board the train towards ${to.name}",
            titleKn = "೪. ${to.nameKn} ಕಡೆಗೆ ರೈಲು ಹತ್ತಿ",
            instructionEn = "Check the direction board above the platform. Wait behind the yellow line. Board when doors open.",
            instructionKn = "ಪ್ಲಾಟ್‌ಫಾರ್ಮ್‌ನ ಮೇಲಿನ ದಿಕ್ಕು ಫಲಕ ನೋಡಿ. ಹಳದಿ ಗೆರೆಯ ಹಿಂದೆ ನಿಲ್ಲಿ. ಬಾಗಿಲು ತೆರೆದಾಗ ಹತ್ತಿ.",
            drawableRes = com.mindmatrix.nammametro.R.drawable.illustration_train
        )

        if (route.hasInterchange && (route.interchangeStation != null)) {
            val ic = route.interchangeStation
            val nextLine = if (firstLine == Line.PURPLE) Line.GREEN else Line.PURPLE
            steps += VisualStep(
                titleEn = "5. Interchange at ${ic.name}",
                titleKn = "೫. ${ic.nameKn} ನಲ್ಲಿ ಮಾರ್ಗ ಬದಲಾಯಿಸಿ",
                instructionEn = "Get down at ${ic.name}. Follow the ${nextLine.displayName.lowercase()} sign. Take the stairs down to the ${nextLine.displayName.lowercase()} platform.",
                instructionKn = "${ic.nameKn} ನಲ್ಲಿ ಇಳಿಯಿರಿ. ${nextLine.displayName} ಚಿಹ್ನೆ ಅನುಸರಿಸಿ. ಕೆಳಗಿನ ಪ್ಲಾಟ್‌ಫಾರ್ಮ್‌ಗೆ ಹೋಗಿ.",
                drawableRes = com.mindmatrix.nammametro.R.drawable.illustration_interchange
            )
            steps += VisualStep(
                titleEn = "6. Board ${nextLine.displayName} train",
                titleKn = "೬. ${nextLine.displayName} ರೈಲು ಹತ್ತಿ",
                instructionEn = "Check the direction board. Board the ${nextLine.displayName.lowercase()} train towards ${to.name}.",
                instructionKn = "ದಿಕ್ಕು ಫಲಕ ನೋಡಿ. ${to.nameKn} ಕಡೆಗೆ ${nextLine.displayName} ರೈಲು ಹತ್ತಿ.",
                drawableRes = if (nextLine == Line.PURPLE) com.mindmatrix.nammametro.R.drawable.illustration_platform_purple
                    else com.mindmatrix.nammametro.R.drawable.illustration_platform_green
            )
        }

        val finalStepNum = steps.size + 1
        steps += VisualStep(
            titleEn = "$finalStepNum. Get down at ${to.name}",
            titleKn = "$finalStepNum. ${to.nameKn} ನಲ್ಲಿ ಇಳಿಯಿರಿ",
            instructionEn = "Stay alert. When the announcement says \"${to.name}\", get down. Walk towards the EXIT sign.",
            instructionKn = "ಗಮನ ಇರಲಿ. \"${to.nameKn}\" ಎಂದು ಪ್ರಕಟಿಸಿದಾಗ ಇಳಿಯಿರಿ. EXIT ಚಿಹ್ನೆ ಕಡೆಗೆ ಹೋಗಿ.",
            drawableRes = com.mindmatrix.nammametro.R.drawable.illustration_exit
        )

        val finalStepNum2 = steps.size + 1
        steps += VisualStep(
            titleEn = "$finalStepNum2. Drop token at exit gate",
            titleKn = "$finalStepNum2. ನಿರ್ಗಮನ ಗೇಟ್‌ನಲ್ಲಿ ಟೋಕನ್ ಹಾಕಿ",
            instructionEn = "Drop the token into the slot on the exit gate. The doors open. You are out!",
            instructionKn = "ನಿರ್ಗಮನ ಗೇಟ್‌ನ ರಂಧ್ರದಲ್ಲಿ ಟೋಕನ್ ಹಾಕಿ. ಬಾಗಿಲು ತೆರೆಯುತ್ತದೆ. ನೀವು ಹೊರಗಿದ್ದೀರಿ!",
            drawableRes = com.mindmatrix.nammametro.R.drawable.illustration_exit_gate
        )

        return steps
    }

    fun tokenMachineSteps(): List<VisualStep> = listOf(
        VisualStep(
            "Step 1: Find the token machine",
            "ಹಂತ ೧: ಟೋಕನ್ ಯಂತ್ರ ಹುಡುಕಿ",
            "Walk to the kiosk near the entry. Look for the screen saying \"Buy Token\".",
            "ಪ್ರವೇಶದ ಬಳಿಯ ಯಂತ್ರಕ್ಕೆ ಹೋಗಿ. \"Buy Token\" ಎಂಬ ಪರದೆ ಹುಡುಕಿ.",
            com.mindmatrix.nammametro.R.drawable.illustration_token
        ),
        VisualStep(
            "Step 2: Choose your destination",
            "ಹಂತ ೨: ನಿಮ್ಮ ಗಮ್ಯಸ್ಥಾನ ಆಯ್ಕೆಮಾಡಿ",
            "Tap the station name on the screen. The fare appears.",
            "ಪರದೆಯ ಮೇಲೆ ನಿಲ್ದಾಣದ ಹೆಸರು ಒತ್ತಿ. ಟಿಕೆಟ್ ಬೆಲೆ ಕಾಣಿಸುತ್ತದೆ.",
            com.mindmatrix.nammametro.R.drawable.illustration_screen
        ),
        VisualStep(
            "Step 3: Insert money",
            "ಹಂತ ೩: ಹಣ ಹಾಕಿ",
            "Insert coins or notes. Machine accepts ₹10, ₹20, ₹50, ₹100. UPI also accepted.",
            "ನಾಣ್ಯ ಅಥವಾ ನೋಟು ಹಾಕಿ. ₹೧೦, ₹೨೦, ₹೫೦, ₹೧೦೦ ಸ್ವೀಕರಿಸುತ್ತದೆ. UPI ಸಹ ಲಭ್ಯ.",
            com.mindmatrix.nammametro.R.drawable.illustration_money
        ),
        VisualStep(
            "Step 4: Collect token and change",
            "ಹಂತ ೪: ಟೋಕನ್ ಮತ್ತು ಚಿಲ್ಲರೆ ತೆಗೆದುಕೊಳ್ಳಿ",
            "A round plastic token drops out. Take it. Take any change too.",
            "ಒಂದು ದುಂಡಗಿನ ಪ್ಲಾಸ್ಟಿಕ್ ಟೋಕನ್ ಬೀಳುತ್ತದೆ. ಅದನ್ನು ತೆಗೆದುಕೊಳ್ಳಿ. ಚಿಲ್ಲರೆ ಸಹ.",
            com.mindmatrix.nammametro.R.drawable.illustration_token
        )
    )

    fun journeyTips(route: Route): List<String> {
        val tips = mutableListOf<String>()
        tips += "Best time to travel: 11 AM – 4 PM. Avoid 9 AM and 6 PM (very crowded)."
        if (route.hasInterchange) {
            tips += "You will change trains at Majestic. Allow 5 extra minutes for the switch."
        }
        if (route.totalStops > 15) {
            tips += "Long journey — carry water. Each train has separate ladies' coach (first coach)."
        }
        val lineArrivalTip = if (route.steps.first().station.line == Line.PURPLE) {
            "Purple Line trains arrive every 4–5 minutes during the day."
        } else {
            "Green Line trains arrive every 5–6 minutes during the day."
        }
        tips += lineArrivalTip
        tips += "Eating, drinking, and chewing gum are not allowed inside the train."
        return tips
    }
}
