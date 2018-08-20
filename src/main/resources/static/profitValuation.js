function valuateProfits() {
    //working only for XXXPLN pairs
    var openTransactions = document.getElementsByClassName("openTransactionsRowID");

    for (i = 0; i < openTransactions.length; i++) {
        var opentTransactionChildren = openTransactions[i].children;

        if (opentTransactionChildren[1].innerHTML === "BUY") {
            console.log("buy trx");
            console.log("buyModalArgument.length " + buyModalArgument.length);

            for (j = 0; j < sellModalArgument.length; j++) {
                var sellArgumentToSplit = sellModalArgument[j].split(",");

                if (opentTransactionChildren[0].innerHTML === sellArgumentToSplit[0].substr(1, 7)) {

                    opentTransactionChildren[6].innerHTML = Math.round(((opentTransactionChildren[5].innerHTML * sellArgumentToSplit[1]) - (opentTransactionChildren[5].innerHTML * opentTransactionChildren[4].innerHTML)) * 100) / 100

                    console.log("amount1: " + (opentTransactionChildren[5].innerHTML * sellArgumentToSplit[1]));
                    console.log("amount2: " + (opentTransactionChildren[5].innerHTML * opentTransactionChildren[4].innerHTML));
                }
            }
        } else {
            for (j = 0; j < buyModalArgument.length; j++) {
                var buyArgumentToSplit = buyModalArgument[j].split(",");

                if (opentTransactionChildren[0].innerHTML === buyArgumentToSplit[0].substr(1, 7)) {

                    opentTransactionChildren[6].innerHTML = Math.round(((opentTransactionChildren[5].innerHTML * opentTransactionChildren[4].innerHTML) - (opentTransactionChildren[5].innerHTML * buyArgumentToSplit[1])) * 100) / 100
                }
            }
        }
    }
}