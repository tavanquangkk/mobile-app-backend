package jp.trial.grow_up.service.client;

import jp.trial.grow_up.controller.client.SkillController;
import jp.trial.grow_up.domain.client.Skill;
import jp.trial.grow_up.repository.client.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SkillService {

    //依存関係
    private final SkillRepository skillRepository;
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
    //依存関係

    //作成
    public Skill createSkill(String name){
        boolean isExisted = this.skillRepository.existsByName(name);
        if(!isExisted){
            return null;
        }
        Skill skill = new Skill();
        skill.setName(name);
        return this.skillRepository.save(skill);
    }

    //Skill 情報取得
    public Skill getSkill(int id){
        return this.skillRepository.findById(id).orElseThrow(()-> new RuntimeException("このスキルが見つかりませんでした"));
    }

    //全てskill一覧取得
    public List<Skill> getAllSkills(){
        return this.skillRepository.findAll();
    }

    //Skill 編集
    public Skill updateSkill(int id,String name){
        Skill currentSkill = this.skillRepository.findById(id).orElseThrow(()-> new RuntimeException("このスキルが見つかりませんでした"));
        if(currentSkill != null){
            currentSkill.setName(name);
            this.skillRepository.save(currentSkill);
            return currentSkill;
        }
        return null;
    }

    //Skill 削除
    public boolean deleteSkill(int id){
        boolean isExisted = this.skillRepository.existsById(id);
        if(!isExisted){
            return false;
        }
        Skill skill = this.skillRepository.findById(id).orElseThrow(()-> new RuntimeException("このスキルが見つかりませんでした"));
        this.skillRepository.delete(skill);
        return  true;
    }




}
