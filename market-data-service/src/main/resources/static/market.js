let orderBookSocket = null;


// -----------------------------
// Market Overview
// -----------------------------

function loadMarkets() {

    fetch("/api/markets")
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to fetch market data");
            }

            return response.json();
        })
        .then(markets => {

            const table =
                document.getElementById("marketTable");

            table.innerHTML = "";

            markets.forEach(market => {

                const row =
                    document.createElement("tr");

                row.innerHTML = `
                    <td>${market.symbol}</td>
                    <td>${market.lastPrice}</td>
                    <td>${market.change24h}%</td>
                    <td>${market.volume24h}</td>
                `;

                table.appendChild(row);
            });
        })
        .catch(error => {

            console.error(
                "Failed to load market data:",
                error
            );
        });
}


// -----------------------------
// Order Book WebSocket
// -----------------------------

function connectOrderBook() {

    const symbol =
        document.getElementById("symbol").value;

    // Close existing connection
    if (orderBookSocket) {
        orderBookSocket.close();
    }

    const protocol =
        window.location.protocol === "https:"
            ? "wss"
            : "ws";

    const socketUrl =
        `${protocol}://${window.location.host}/ws/orderbook?username=admin`;

    orderBookSocket =
        new WebSocket(socketUrl);


    orderBookSocket.onopen = function() {

        document.getElementById(
            "connectionStatus"
        ).textContent =
            `Connected - ${symbol}`;

        // Send selected symbol to backend
        orderBookSocket.send(symbol);
    };


    orderBookSocket.onmessage = function(event) {

        console.log(
            "Order book message:",
            event.data
        );

        try {

            const response =
                JSON.parse(event.data);

            if (!response.data ||
                response.data.length === 0) {

                return;
            }

            const orderBook =
                response.data[0];


            updateOrders(
                "bids",
                orderBook.bids
            );

            updateOrders(
                "asks",
                orderBook.asks
            );

        } catch (error) {

            console.error(
                "Failed to parse order book:",
                error
            );
        }
    };


    orderBookSocket.onerror = function(error) {

        console.error(
            "WebSocket error:",
            error
        );

        document.getElementById(
            "connectionStatus"
        ).textContent =
            "Connection error";
    };


    orderBookSocket.onclose = function() {

        document.getElementById(
            "connectionStatus"
        ).textContent =
            "Disconnected";
    };
}


// -----------------------------
// Update Order Book
// -----------------------------

function updateOrders(elementId, orders) {

    const table =
        document.getElementById(elementId);

    table.innerHTML = "";

    if (!orders) {
        return;
    }

    orders
        .slice(0, 15)
        .forEach(order => {

            const row =
                document.createElement("tr");

            row.innerHTML = `
                <td>${order[0]}</td>
                <td>${order[1]}</td>
            `;

            table.appendChild(row);
        });
}


// -----------------------------
// Button
// -----------------------------

document
    .getElementById("connectButton")
    .addEventListener(
        "click",
        connectOrderBook
    );


// -----------------------------
// Initial Market Load
// -----------------------------

loadMarkets();


// Refresh market overview every 5 seconds

setInterval(
    loadMarkets,
    5000
);