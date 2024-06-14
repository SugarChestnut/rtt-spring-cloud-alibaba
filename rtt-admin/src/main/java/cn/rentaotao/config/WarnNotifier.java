package cn.rentaotao.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 当实例状态变更的时候，通知管理员
 *
 * @author rtt
 * @date 2024/6/6 15:30
 */
@Component
public class WarnNotifier extends AbstractStatusChangeNotifier {

    public WarnNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        System.out.println("实例状态变更警告");
        return Mono.fromRunnable(() -> {
        });
    }
}
