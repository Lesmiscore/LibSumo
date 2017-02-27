import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.command.CommandSender

/**
 * Created by nao on 2017/02/27.
 */
class Extender {
    static void init(){
        Server.metaClass.remove={Object o->
            def playerName=null
            if(o instanceof CharSequence){
                playerName=o.toString()
            }else if(o instanceof CommandSender){
                playerName=o.name
            }
            if(playerName!=null){
                def player=Server.instance.getPlayerExact(playerName)
                player.kick()
            }
        }
        Player.metaClass.getExperiences={
            new Experiences(delegate,false)
        }
        Player.metaClass.getExperienceLevels={
            new Experiences(delegate,true)
        }
    }

    static class Experiences{
        private Player p
        private boolean isLevel
        Experiences(Player p,boolean isLevel){
            this.p=p
            this.isLevel=isLevel
        }

        void add(int amount){
            if(isLevel){
                def exp=p.experience
                def lev=p.experienceLevel
                p.setExperience(exp,lev+amount)
            }else{
                p.addExperience(amount)
            }
        }

        void remove(int amount){
            if(isLevel){
                def exp=p.experience
                def lev=p.experienceLevel
                p.setExperience(exp,lev-amount)
            }else{
                def exp=p.experience
                def lev=p.experienceLevel
                p.setExperience(exp-amount,lev)
            }
        }
    }
}
