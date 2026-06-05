function showPanel(panelName) {
    document.getElementById("profile-panel").classList.add("hidden");
    document.getElementById("notifications-panel").classList.add("hidden");
    document.getElementById("books-panel").classList.add("hidden");
    document.getElementById("reservations-panel").classList.add("hidden");

    document.getElementById(panelName + "-panel").classList.remove("hidden");
}

function closePanels() {
    document.getElementById("profile-panel").classList.add("hidden");
    document.getElementById("notifications-panel").classList.add("hidden");
    document.getElementById("books-panel").classList.add("hidden");
    document.getElementById("reservations-panel").classList.add("hidden");
}

let selectedSeat = null;
let isInWaitingList = false;

function getCurrentTime() {
    return new Date().toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit"
    });
}

function updateNotification(message) {
    const timeElement = document.getElementById("notification-time");
    const messageElement = document.getElementById("notification-message");

    if (timeElement && messageElement) {
        timeElement.innerText = getCurrentTime();
        messageElement.innerText = message;
    }
}

function selectSeat(button, seatNumber) {
    document.querySelectorAll(".seat.available").forEach(seat => {
        seat.classList.remove("selected");
    });

    button.classList.add("selected");
    selectedSeat = seatNumber;

    document.getElementById("selected-seat-text").innerText =
        "Selected Seat: " + seatNumber;
}

function reserveSelectedSeat() {
    if (selectedSeat === null) {
        document.getElementById("modal-title").innerText = "Seat Selection Required";
        document.getElementById("confirm-message").innerText =
            "Please select a seat before making a reservation.";

        const modal = document.getElementById("confirm-modal");
        modal.setAttribute("data-action", "warning");
        modal.classList.remove("hidden");
        return;
    }

    document.getElementById("modal-title").innerText = "Confirm Reservation";
    document.getElementById("confirm-message").innerText =
        "Are you sure you want to reserve Seat " + selectedSeat + "?";

    const modal = document.getElementById("confirm-modal");
    modal.setAttribute("data-action", "reserve-seat");
    modal.classList.remove("hidden");
}

function closeModal() {
    const modal = document.getElementById("confirm-modal");
    modal.classList.add("hidden");
    modal.removeAttribute("data-action");
}

function confirmReservation() {
    const modal = document.getElementById("confirm-modal");
    const action = modal.getAttribute("data-action");

    modal.classList.add("hidden");

    if (action === "warning") {
        modal.removeAttribute("data-action");
        return;
    }

    if (action === "reserve-seat") {
        finishReservation();
        modal.removeAttribute("data-action");
        return;
    }

    if (action === "leave-waiting-list") {
        leaveWaitingList();
        modal.removeAttribute("data-action");
        return;
    }

    if (action === "cancel-reservation") {
        cancelReservation();
        modal.removeAttribute("data-action");
        return;
    }
}

function finishReservation() {
    const selectedButton = document.querySelector(".seat.selected");

    if (!selectedButton) {
        return;
    }

    selectedButton.classList.remove("available");
    selectedButton.classList.remove("selected");
    selectedButton.classList.add("your-seat");
    selectedButton.disabled = true;

    document.getElementById("selected-seat-text").innerText =
        "Your Seat: " + selectedSeat;

    const reservedSeatDisplay = document.getElementById("reserved-seat-display");
    const cancelReservationIcon = document.getElementById("cancel-reservation-icon");

    if (reservedSeatDisplay) {
        reservedSeatDisplay.innerText = "Seat " + selectedSeat;
    }

    if (cancelReservationIcon) {
        cancelReservationIcon.classList.remove("hidden");
    }

    updateNotification("Seat " + selectedSeat + " reserved successfully.");

    selectedSeat = null;

    showPanel("notifications");
}

function joinWaitingList() {
    isInWaitingList = true;

    document.getElementById("waiting-status").innerText =
        "You are in the waiting list.";

    document.getElementById("waiting-position").innerText =
        "Position: 1";

    document.getElementById("waiting-button").innerText =
        "Leave Waiting List";

    document.getElementById("waiting-button")
        .setAttribute("onclick", "askLeaveWaitingList()");

    document.getElementById("waiting-count").innerText = "1";

    updateNotification(
        "You have joined the waiting list. You will be notified when a seat becomes available."
    );
}

function askLeaveWaitingList() {
    document.getElementById("modal-title").innerText = "Leave Waiting List";

    document.getElementById("confirm-message").innerText =
        "Are you sure you want to leave the waiting list?";

    const modal = document.getElementById("confirm-modal");
    modal.setAttribute("data-action", "leave-waiting-list");
    modal.classList.remove("hidden");
}

function leaveWaitingList() {
    isInWaitingList = false;

    document.getElementById("waiting-status").innerText =
        "You are not in the waiting list.";

    document.getElementById("waiting-position").innerText =
        "Position: -";

    document.getElementById("waiting-button").innerText =
        "Join Waiting List";

    document.getElementById("waiting-button")
        .setAttribute("onclick", "joinWaitingList()");

    document.getElementById("waiting-count").innerText = "0";

    updateNotification("You have left the waiting list.");
}

function askCancelReservation() {
    const modal = document.getElementById("confirm-modal");

    const seatText =
        document.getElementById("my-seat-number")?.innerText ||
        document.getElementById("reserved-seat-display")?.innerText ||
        "your seat";

    document.getElementById("modal-title").innerText = "Cancel Reservation";
    document.getElementById("confirm-message").innerText =
        "Are you sure you want to cancel " + seatText + "?";

    modal.setAttribute("data-action", "cancel-reservation");
    modal.classList.remove("hidden");
}

function cancelReservation() {
    const mySeatNumber = document.getElementById("my-seat-number");
    const reservationStatus = document.getElementById("reservation-status");
    const reservedSeatDisplay = document.getElementById("reserved-seat-display");
    const cancelReservationIcon = document.getElementById("cancel-reservation-icon");

    if (mySeatNumber) {
        mySeatNumber.innerText = "None";
    }

    if (reservationStatus) {
        reservationStatus.innerText = "Cancelled";
    }

    if (reservedSeatDisplay) {
        reservedSeatDisplay.innerText = "No Active Reservation";
    }

    if (cancelReservationIcon) {
        cancelReservationIcon.classList.add("hidden");
    }

    updateNotification("Your reservation has been cancelled successfully.");
}

function simulateNotification() {
    updateNotification(
        "Reservation expired: Halil did not check in within 15 minutes. Seat 101 is now available. Observer notified Asma from the waiting list."
    );

    const waitingStatus = document.getElementById("waiting-status");
    const waitingPosition = document.getElementById("waiting-position");

    if (waitingStatus && waitingPosition) {
        waitingStatus.innerText = "You received a seat availability notification.";
        waitingPosition.innerText = "Position: Notified";
    }

    showPanel("notifications");
}