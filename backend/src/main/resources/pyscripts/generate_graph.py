import psycopg2
from dotenv import load_dotenv
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
import json
import base64
import sys
import os


# Retrieve a survey result set by id from DB
# EDIT: Data will now be passed from spring back-end call, database call may still be used in future
def create_connection():
    load_dotenv()
    db_name = os.getenv("POSTGRES_DB")
    db_user = os.getenv("POSTGRES_USER")
    db_password = os.getenv("POSTGRES_PASSWORD")

    connection = psycopg2.connect(
        database=db_name,
        user=db_user,
        password=db_password,
        host="localhost",
        port='5432'
    )

    cursor = connection.cursor()
    return connection, cursor


def create_table():
    try:
        conn, curr = create_connection()
        try:
            curr.execute("CREATE TABLE IF NOT EXISTS graphs(id INTEGER, name TEXT, graphImg BYTEA)")

        except(Exception, psycopg2.Error) as error:
            print("Error while creating graphs table", error)
        finally:
            conn.commit()
            conn.close()
    finally:
        pass


def write_blob(graph_id, file_path, name):
    try:
        drawing = open(file_path, 'rb').read()
        conn, cursor = create_connection()
        try:
            cursor.execute("INSERT INTO graphs(id,name,graphImg) VALUES(%s,%s,%s)",
                           (graph_id, name, psycopg2.Binary(drawing)))
            conn.commit()
            # Check inserts working correctly
            cursor.execute("SELECT * FROM graphs")
            print(cursor.fetchall())
        except (Exception, psycopg2.DatabaseError) as error:
            print("Error while inserting image into graphs table", error)
        finally:
            conn.close()
    finally:
        pass


def encode_image(image_path):
    data = {}
    with open(image_path, mode='rb') as file:
        img = file.read()

    data['img'] = base64.b64encode(img).decode()
    print(json.dumps(data))


# Graph annotations - Could be pulled from database in future version but would require modification
# to display correctly on graph
def generate_annotations():
    return [
        [' Individualist', ' Predictive', ' Iterative', ' Collaborative', ' Experimental'],
        [' Arbitrary', ' Long-term plan', ' Feature driven', ' Data driven', ' All driven'],
        ['No organisation,\n single contributor', ' Hierarchy', 'Cross-functional\n teams', ' DevOps/SRE',
         'Internal supply\n chains'],
        [' Random', ' Waterfall', ' Agile', 'Design Thinking +\n Agile + Lean', 'Distributed,\n self-organised'],
        ['Emerging from\n trial and error', 'Tightly coupled\n monolith', ' Client server', ' Microservices',
         ' Functions'],
        ['Respond to\n users complaints', 'Ad-hoc\n monitoring', ' Alerting', 'Full observability\n & self-healing',
         'Preventative ML,\n AI'],
        ['Irregular\n releases', 'Periodic\n releases', 'Continuous\n Integration', 'Continuous\n Delivery',
         'Continuous\n Deployment'],
        [' Manual', ' Scripted', ' Config management', ' Orchestration', ' Serverless'],
        [' Single\n server', ' Multiple\n servers', ' VMs', 'Containers/\n hybrid cloud', ' Edge\n computing']
    ]


def generate_dictionary(results):
    vals = results.split(',')
    return {'CULTURE': float(vals[0]), 'PROD/SERVICE DESIGN': float(vals[1]), 'TEAM': float(vals[2]),
            'PROCESS': float(vals[3]), 'ARCHITECTURE': float(vals[4]), 'MAINTENANCE': float(vals[5]),
            'DELIVERY': float(vals[6]), 'PROVISIONING': float(vals[7]), 'INFRASTRUCTURE': float(vals[8])}


def generate_graph(result, ann, request):
    j = 0
    x_axis = [key for key, value in result.items()]
    y_axis = [value for key, value in result.items()]

    cloud_native = [4 for number in range(9)]

    fig, ax = plt.subplots(figsize=(12, 7.5))

    for axis in [ax.xaxis, ax.yaxis]:
        axis.set_major_locator(ticker.MaxNLocator(integer=True))

    ax.plot(x_axis, y_axis, marker='.', color='#325DA4', linestyle='dotted')
    ax.plot(x_axis, cloud_native, marker='.', color='#F53796', linestyle='dotted')
    ax.fill_between(x_axis, y_axis, cloud_native, color='#FDD6E9')
    ax.fill_between(x_axis, y_axis, color='#D8DEEC')

    for key in result.keys():
        for i in range(1, 6):
            ax.annotate(ann[j][i - 1], (key, i), fontsize=7)
            plt.plot(key, i, marker='o', markersize=0.75, color='black')
        j += 1

    labels = ['', 'No Process', 'Waterfall', 'Agile', 'Cloud Native', 'Next']

    ax.set(ylim=(0, 6))
    ax.set_yticklabels(labels)

    font1 = {'family': 'serif', 'color': 'blue', 'size': 20}
    font2 = {'family': 'serif', 'color': 'darkred', 'size': 15}

    plt.ylabel('Stage', fontdict=font2)
    plt.xticks(rotation=90)
    plt.xlabel('Area', fontdict=font2)
    plt.title('Cloud Nativity Matrix', fontdict=font1)
    plt.grid(axis='x')
    plt.tight_layout()
    # Plot needs to be saved as image before upload to DB as bytea and conversion to JSON object
    # to be sent to java backend, will be deleted after both are complete
    plt.savefig(str(request) + '_graph.png')
    # plt.show()


# Testing results dictionary
# dictionary = generate_dictionary("2,3,4,3,2.5,3,2.5,2,4,3")
# Retrieve results from java backend
dictionary = generate_dictionary(sys.argv[1])
request_id = sys.argv[2]
# request_id = 1

annotations = generate_annotations()
generate_graph(dictionary, annotations, request_id)
# create_table()
# write_blob(request_id, str(request_id) + "_graph.png", "Test")

encode_image(str(request_id) + '_graph.png')

# Delete saved image
os.remove(str(request_id) + '_graph.png')
