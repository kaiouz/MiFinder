package top.codemaster.mifinder.di;

import dagger.Component;
import top.codemaster.mifinder.resources.ResourcesViewModel;

@ServiceScope
@Component(modules = {ServiceModule.class}, dependencies = AppComponent.class)
public interface ServiceComponent {

    void inject(ResourcesViewModel resourcesViewModel);


}
