

import json
import os
import sys
import requests
from time import sleep
from sanic import Sanic
from mcstatus import JavaServer
from faker import Faker
app = Sanic('qb')
base_path = "LightBot/"
adminlist = ['1487084645']
@app.websocket('/qb')
async def qqbot(request, ws):
#检测QQBot-Remake是否存在
    
    if not os.path.exists("LightBot"):
        
        os.mkdir("LightBot")
        open(base_path + "data.json")
    if not os.path.exists("LightBot/data.json"):
        
        open('LightBot/data.json','a',encoding='utf-8')
    print("已经生成QQBot-Remake文件")    
    faker = Faker(locale="zh-CN")
    exitkey = faker.pystr(16)
    print("\n退出密钥:" + exitkey + "\n已写入文件\n")
    with open("exitKey.txt","w") as f:
        f.write(exitkey)
    spamkey = faker.pystr(16)
    print("\n刷屏密钥:" + spamkey + "\n已写入文件\n")
    with open("spamKey.txt","w") as f:
        f.write(spamkey)
    
    """QQ机器人"""
    while True:
        empty = ""
        data = await ws.recv()
        data = json.loads(data)
        
        print(json.dumps(data, indent=4, ensure_ascii=False))
        # if 判断是群消息且文本消息不为空
        if data.get('message_type') == 'group' and data.get('raw_message'):
            raw_message = data['raw_message']
            qq = data['sender']['user_id']
            nickname = data['sender']['nickname']
            card = data['sender']['card']
            profile = [str(qq),'User',100]
            # Local
            
            money = profile[2]
            money = str(money).split(' ',4)
            money = str(money).replace("[","").replace("]","").replace("'","")
            if raw_message == 'help':
                msg = "在线QQBot-Remake Wiki:\nhttps://github.com/StarryYurnu/QQBot-Remake/wiki\n若无法连接，请输入help text 获取文档版（简短）"
                ret = { 
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message': msg,
                        }
                    }
                await ws.send(json.dumps(ret))
            elif raw_message.startswith('register'):
                if not os.path.exists('LightBot/' + str(qq)+ '.txt'):
                    
                
 
                    ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message':  '注册成功',
                        }
                    }
                    await ws.send(json.dumps(ret))
                    #profile = str(profile)
                    #content = "'" + str(qq) + "'," + "'User'," + str(100)
                    content = str(qq) + ' User' + ' 100'
                    with open(base_path + str(qq) + ".txt",'a',encoding='utf-8') as data:
                        data.write(content)
                        data.close()
                   
                if os.path.exists('LightBot/' + str(qq)+ ".txt"):
                    ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message':  '你已经注册过了',
                        }
                    }
                    await ws.send(json.dumps(ret))
            elif raw_message.startswith('mcstat'):
                raw_message = raw_message[7:100]
                if ":" not in raw_message:
                    raw_message += ":25565"
                    ret = { 
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message': "未填写端口，自动补全端口25565",
                        }
                    }
                server = JavaServer.lookup(raw_message)
                status = server.status()
                ret = { 
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message': "IP:" + str(raw_message) + "\n在线人数:" + str(status.players.online) + "\n延迟:" + str(int(status.latency)) + "ms",
                        }
                    }
                await ws.send(json.dumps(ret))
            elif raw_message == "randata":
                randata = "成功生成伪个人信息:\n" + "姓名:" + str(faker.name()) + "\n身份证:" + str(faker.ssn()) + "\n手机号:" + str(faker.phone_number()) + "\n信用卡号:" + str(faker.credit_card_number()) + "\n住址:" + str(faker.address()) + "\n邮箱:" + str(faker.company_email()) + "\n网名:" + str(faker.user_name()) + str(faker.random_digit())
                ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message': randata,
                        }
                    }
                await ws.send(json.dumps(ret))
            elif "exit " + exitkey in raw_message:
                ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message': "成功验证密钥，将在5秒后关闭机器人...",
                        }
                    } 
                await ws.send(json.dumps(ret))
                ws.send('exit')
                sleep(5)
                sys.exit()
                
                    
                
            elif raw_message == "author":
  
                ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message': "This bot by 1487084645",
                        }
                    }
                await ws.send(json.dumps(ret))
            elif "spam " + spamkey in raw_message:
                spamKeyLength = int(len(spamkey)) + 6
                spam_message = raw_message[spamKeyLength:2147483647]
                while True:
                    if spam_message == "":
                        spam_message = "spam"
  
                    ret = {
                    'action': 'send_group_msg',
                    'params': {
                        'group_id': data['group_id'],
                        'message': spam_message,
                        }
                    }
                    sleep(0.1)   
                    
                
                    await ws.send(json.dumps(ret))
            elif raw_message.startswith('say '):      #Admin module
                with open('QQBot-Remake/' + str(qq) + ".txt",'r') as file:
                    content = file.read()
                    file.close()
                profile = content.split(' ',4) 
                perm = profile[1]
                perm = perm.split(' ',4)
                
                if str(perm) == "['Admin']":
                    chat = raw_message[4:2147483647]
                    ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message': chat,
                        }
                    }
                    await ws.send(json.dumps(ret))
                else: 
                    ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message': "权限不足",
                        }
                    }
                    await ws.send(json.dumps(ret))
            elif raw_message.startswith('骰子'):
                if card == empty:
                    card = nickname
                dian = str(faker.random_int(1,6)) + "点"
                
                ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message':  card + ",你投中了：" + str(dian) + "!",
                        }
                    }
                await ws.send(json.dumps(ret))
            elif raw_message.startswith('余额'):
                other_qq = raw_message[3:114514114514]
                with open('QQBot-Remake/' + str(qq) + ".txt",'r') as file:
                    content = file.read()
                    file.close()
                if other_qq == empty:
                    ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message':  card + ",你的余额：" + str(money) + "金币"
                        }
                    }
                    await ws.send(json.dumps(ret))
                else:

                        if not os.path.exists('QQBot-Remake/' + str(other_qq) + ".txt"):
                           ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message':  "账户不存在，请检测QQ号",
                        }
                    }   
                        else:
                            with open('LightBot/' + str(other_qq) + ".txt",'r') as file:
                                content = file.read()
                                file.close()
                                profile = content.split(' ',4) 
                                othermoney = profile[2]
                                othermoney = str(othermoney).split(' ',4)
                                othermoney = str(othermoney).replace("[","").replace("]","").replace("'","")
                            ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message':  other_qq + "的余额：" + str(othermoney) + "金币"
                        }
                    }
                        await ws.send(json.dumps(ret))
            elif raw_message == "签到":
                with open('LightBot/' + str(qq) + ".txt",'r') as file:
                    content = file.read()
                    file.close()
                    profile = content.split(' ',4) 
                    qid = profile[0]
                    perms = profile[1]
                    bal = profile[2]
                    qid = str(qid).split(' ',4)
                    qid = str(qid).replace("[","").replace("]","").replace("'","")
                    perms = str(perms).split(' ',4)
                    perms = str(perms).replace("[","").replace("]","").replace("'","")
                    bal = str(bal).split(' ',4)
                    bal = str(bal).replace("[","").replace("]","").replace("'","")
                    bal = int(bal) + 50
                    content = str(qq) + " " + str(perms) + " "+ str(bal)
                    with open('QQBot-Remake/' + str(qq) + ".txt",'w') as file:
                        file.write(content)
                        file.close()

                    ret = {
                        'action': 'send_group_msg',
                        'params': {
                            'group_id': data['group_id'],
                            'message':  "已获得50金币",
                        }
                    }
                    await ws.send(json.dumps(ret))

                
                

                
if __name__ == '__main__':
    app.run(debug=True,port=5678,auto_reload=True)
