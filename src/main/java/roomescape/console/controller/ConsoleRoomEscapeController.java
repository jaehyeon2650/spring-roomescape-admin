package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.constant.MenuOption;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.service.ReservationService;

@Controller
public class ConsoleRoomEscapeController {
    private final ReservationService reservationService;
    private final OutputView outputView;
    private final InputView inputView;

    public ConsoleRoomEscapeController(ReservationService reservationService,
                                       OutputView outputView,
                                       InputView inputView) {
        this.reservationService = reservationService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        outputView.printReservationInfo(reservationService.findAllReservations(),
                reservationService.findAllReservationTimes());
        MenuOption option = inputView.getMenuOption();
        while (option != MenuOption.EXIT) {
            if (option == MenuOption.RESERVATION_ADD) {
                ReservationRequest request = inputView.getReservationAddRequest();
                reservationService.createReservation(request);
            }
            if (option == MenuOption.RESERVATION_DELETE) {
                Long deleteId = inputView.getDeleteReservationId();
                reservationService.deleteReservation(deleteId);
            }
            if (option == MenuOption.RESERVATION_TIME_ADD) {
                ReservationTimeRequest request = inputView.getReservationTimeAddRequest();
                reservationService.createReservationTime(request);
            }
            if (option == MenuOption.RESERVATION_TIME_DELETE) {
                Long deleteId = inputView.getDeleteReservationTimeId();
                reservationService.deleteReservationTime(deleteId);
            }
            outputView.printReservationInfo(reservationService.findAllReservations(),
                    reservationService.findAllReservationTimes());
            option = inputView.getMenuOption();
        }
    }
}
