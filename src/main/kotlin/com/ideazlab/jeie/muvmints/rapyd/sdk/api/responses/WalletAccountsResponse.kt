package com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses

import com.ideazlab.jeie.muvmints.rapyd.sdk.Response
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydStatus
import com.ideazlab.jeie.muvmints.rapyd.sdk.api.responses.embedded.RapydWalletAccount

@Response
data class WalletAccountsResponse(
    val status: RapydStatus,
    val data: List<RapydWalletAccount>
)